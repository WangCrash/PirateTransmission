package GUI.Transmisiones;

import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import GUI.OneArgumentRunnableObject;
import Managers.Helpers.LastFMManager;
import Model.Artista;
import Model.Transmision;
import Utils.UtilTools;

@SuppressWarnings("serial")
public class ArtistTransmisionCell extends TransmisionCell {
	private Artista artist;
	/**
	 * Create the panel.
	 */
	public ArtistTransmisionCell(JFrame mainFrame, TransmisionesView parentView, Transmision transmission) {
		super(mainFrame, parentView, transmission);
		getRatingImageLabel().setIcon(new ImageIcon(FilmTransmisionCell.class.getResource("/images/transmission-rated.png")));
		this.artist = (Artista)transmission.getHelperItem();
		initLabels();
	}

	@Override
	protected void initLabels() {
		super.getItemTypeLabel().setText("ARTISTA");
		super.getTitleLabel().setText(artist.getNombre());
		if(artist.getImageURL() != null){
			Thread itemImageThread = new Thread(new OneArgumentRunnableObject(artist.getImageURL()) {
				
				@Override
				public void run() {
					getItemImage((URL) this.getArgument());
				}
			});
			itemImageThread.start();
		}
		//super.getDateLabel().setText(transmission.getFecha().toString());
		setRattingButtonText();
//		super.getCustomTagLabel1().setText("Artista: ");
//		super.getCustomTagLabel1().setVisible(true);
//		super.getCustomFieldLabel1().setText(this.artist.getArtista());
//		super.getCustomFieldLabel1().setVisible(true);
		setAppropiateRatingImage();
	}
	
	private void setRattingButtonText(){
		if(artist.getIsRated()){
			getCustomButton().setText("No me Gusta");
		}else{
			getCustomButton().setText("Me Gusta");
		}
	}

	private void getItemImage(URL imageURL) {
		ImageIcon image = new ImageIcon(imageURL);
		//super.getItemImageLabel().setBorder(null);
		System.out.println("Ancho: " + (int)super.getItemImageLabel().getSize().width + ", Alto:" + (int)super.getItemImageLabel().getSize().getHeight());
		super.getItemImageLabel().setIcon(new UtilTools().getScaledImage(image.getImage(), 130, 110));
	}

	@Override
	protected void rateItem() {
		if(artist.getIsRated()){
			removeItemFromCollection();
			artist.setIsRated(false);
		}else{
			addItemToCollection();
			artist.setIsRated(true);
		}
		setAppropiateRatingImage();
		updateTransmission();
	}
	
	public void addItemToCollection() {
		if(LastFMManager.getInstance().rateItem(artist)){
			artist.setIsRated(true);
			getCustomButton().setText("No me Gusta");
			new UtilTools().showInfoOKDialog(mainFrame, "", "Añadido a tu biblioteca");
			if(!transmission.getRated()){
				transmission.setRated(true);
			}
		}else{
			new UtilTools().showWarningDialog(mainFrame, "Error", "No ha sido posible añadir el artista");
		}
	}
	
	public void removeItemFromCollection(){
		if(LastFMManager.getInstance().removeArtist(artist)){
			artist.setIsRated(false);
			getCustomButton().setText("Me Gusta");
			new UtilTools().showInfoOKDialog(mainFrame, "", "Artista eliminado");
			if(!transmission.getRated()){
				transmission.setRated(true);
			}
		}else{
			new UtilTools().showWarningDialog(mainFrame, "Error", "No se ha podido eliminar el artista");
		}
	}
}
