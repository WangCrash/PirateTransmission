package GUI.Transmissions;


import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import GUI.OneArgumentRunnableObject;
import Managers.Helpers.LastFMManager;
import Model.Disco;
import Model.Transmission;
import Utils.UtilTools;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class AlbumTransmissionCell extends TransmissionCell {
	private Disco album;
	/**
	 * Create the panel.
	 */
	public AlbumTransmissionCell(JFrame mainFrame, TransmissionsView parentView, Transmission transmission) {
		super(mainFrame, parentView, transmission);
		getCustomButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				rateItem();
			}
		});
		getRatingImageLabel().setIcon(new ImageIcon(FilmTransmissionCell.class.getResource("/images/transmission-rated.png")));
		this.album = (Disco)transmission.getHelperItem();
		initLabels();
	}

	@Override
	protected void initLabels() {
		super.getItemTypeLabel().setText("�LBUM");
		super.getTitleLabel().setText(album.getNombre());
		if(album.getImageURL() != null){
			Thread itemImageThread = new Thread(new OneArgumentRunnableObject(album.getImageURL()) {
				
				@Override
				public void run() {
					getItemImage((URL) this.getArgument());
				}
			});
			itemImageThread.start();
		}
		super.getDateLabel().setText(transmission.getFecha().toString());
		if(album.getIsRated()){
			getCustomButton().setText("No me Gusta");
		}else{
			getCustomButton().setText("Me Gusta");
		}
		super.getCustomTagLabel1().setText("Artista: ");
		super.getCustomTagLabel1().setVisible(true);
		super.getCustomFieldLabel1().setText(this.album.getArtista());
		super.getCustomFieldLabel1().setVisible(true);
		setAppropiateRatingImage();
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
	}
	
	public void addItemToCollection() {
		if(LastFMManager.getInstance().rateItem(album)){
			album.setIsRated(true);
			getCustomButton().setText("No me Gusta");
			new UtilTools().showInfoOKDialog(mainFrame, "", "A�adido a tu biblioteca");
		}else{
			new UtilTools().showWarningDialog(mainFrame, "Error", "No ha sido posible a�adir el �lbum");
		}
	}
	
	public void removeItemFromCollection(){
		if(LastFMManager.getInstance().removeAlbum(album)){
			album.setIsRated(false);
			getCustomButton().setText("Me Gusta");
			new UtilTools().showInfoOKDialog(mainFrame, "", "�lbum eliminado");
		}else{
			new UtilTools().showWarningDialog(mainFrame, "Error", "No se ha podido eliminar el �lbum");
		}
	}
}
