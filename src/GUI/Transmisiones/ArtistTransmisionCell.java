package GUI.Transmisiones;

import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import GUI.OneArgumentRunnableObject;
import GUI.Helpers.Results.Items.Music.ArtistCell;
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
		super.getTitleLabel().setToolTipText(artist.getNombre());
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
		setUpRatingButton();
//		super.getCustomTagLabel1().setText("Artista: ");
//		super.getCustomTagLabel1().setVisible(true);
//		super.getCustomFieldLabel1().setText(this.artist.getArtista());
//		super.getCustomFieldLabel1().setVisible(true);
		setAppropiateRatingImage();
	}
	
	private void setUpRatingButton(){
		if(artist.getIsRated()){
			getCustomButton().setIcon(new ImageIcon(ArtistCell.class.getResource("/images/HelperResults/dislike-music-icon.png")));
			getCustomButton().setToolTipText("Eliminar de tu colección");
		}else{
			getCustomButton().setIcon(new ImageIcon(ArtistCell.class.getResource("/images/HelperResults/like-music-icon.png")));
			getCustomButton().setToolTipText("Añadir a tu colección");
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
		setUpRatingButton();
	}
	
	public void addItemToCollection() {
		if(LastFMManager.getInstance().rateItem(artist)){
			artist.setIsRated(true);
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
			new UtilTools().showInfoOKDialog(mainFrame, "", "Artista eliminado");
			if(!transmission.getRated()){
				transmission.setRated(true);
			}
		}else{
			new UtilTools().showWarningDialog(mainFrame, "Error", "No se ha podido eliminar el artista");
		}
	}
}
