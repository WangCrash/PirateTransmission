package GUI.Transmisiones;


import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import GUI.Helpers.Results.Items.Music.ArtistCell;
import Managers.Helpers.LastFMManager;
import Model.Disco;
import Model.Transmision;
import Utils.OneArgumentRunnableObject;
import Utils.UtilTools;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class AlbumTransmisionCell extends TransmisionCell {
	private Disco album;
	/**
	 * Create the panel.
	 */
	public AlbumTransmisionCell(JFrame mainFrame, TransmisionesView parentView, Transmision transmission) {
		super(mainFrame, parentView, transmission);
		getCustomButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				rateItem();
			}
		});
		this.album = (Disco)transmission.getHelperItem();
		setAppropiateRatingImage();
		initLabels();
	}

	@Override
	protected void initLabels() {
		super.getItemTypeLabel().setText("ÁLBUM");
		super.getTitleLabel().setText(album.getNombre());
		super.getTitleLabel().setToolTipText(album.getNombre());
		if(album.getImageURL() != null){
			Thread itemImageThread = new Thread(new OneArgumentRunnableObject(album.getImageURL()) {
				
				@Override
				public void run() {
					System.out.println("Buscando imagen " + (URL) this.getArgument());
					getItemImage((URL) this.getArgument());
				}
			});
			itemImageThread.start();
		}
		//super.getDateLabel().setText(transmission.getFecha().toString());
		setUpRatingButton();
		super.getCustomTagLabel1().setText("Artista: ");
		super.getCustomTagLabel1().setVisible(true);
		super.getCustomFieldLabel1().setText(this.album.getArtista());
		super.getCustomFieldLabel1().setVisible(true);
		setAppropiateRatingImage();
	}
	
	private void setUpRatingButton(){
		if(album.getIsRated()){
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
		super.getItemImageLabel().setIcon(new UtilTools().getScaledImage(image.getImage(), 120, 120));
	}

	@Override
	protected void rateItem() {
		if(album.getIsRated()){
			removeItemFromCollection();
		}else{
			addItemToCollection();
		}
		setAppropiateRatingImage();
		updateTransmission();
		setUpRatingButton();
	}
	
	public void addItemToCollection() {
		if(LastFMManager.getInstance().rateItem(album)){
			album.setIsRated(true);
			new UtilTools().showInfoOKDialog(mainFrame, "", "Añadido a tu biblioteca");
			if(!transmission.getRated()){
				transmission.setRated(true);
			}
		}else{
			new UtilTools().showWarningDialog(mainFrame, "Error", "No ha sido posible añadir el álbum");
		}
	}
	
	public void removeItemFromCollection(){
		if(LastFMManager.getInstance().removeAlbum(album)){
			album.setIsRated(false);
			new UtilTools().showInfoOKDialog(mainFrame, "", "Álbum eliminado");
			if(!transmission.getRated()){
				transmission.setRated(true);
			}
		}else{
			new UtilTools().showWarningDialog(mainFrame, "Error", "No se ha podido eliminar el álbum");
		}
	}
	
	
}
