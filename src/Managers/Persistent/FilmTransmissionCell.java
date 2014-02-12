package Managers.Persistent;

import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import GUI.OneArgumentRunnableObject;
import GUI.Helpers.Results.Items.Films.RateFilm;
import GUI.Helpers.Results.Items.Films.RateMovieView;
import GUI.Transmissions.TransmissionCell;
import GUI.Transmissions.TransmissionsView;
import Model.FichaPelicula;
import Model.Transmission;
import Utils.UtilTools;

@SuppressWarnings("serial")
public class FilmTransmissionCell extends TransmissionCell implements RateFilm{
	private FichaPelicula film;

	/**
	 * Create the panel.
	 */
	public FilmTransmissionCell(JFrame mainFrame, TransmissionsView parentView, Transmission transmission) {
		super(mainFrame, parentView, transmission);
		getRatingImageLabel().setIcon(new ImageIcon(FilmTransmissionCell.class.getResource("/images/transmission-rated.png")));
		this.film = (FichaPelicula)transmission.getHelperItem();
		getCustomButton().setText("Votar");
		initLabels();
	}

	@Override
	protected void initLabels() {
		super.getItemTypeLabel().setText("Película/Serie/Documental");
		super.getTitleLabel().setText(film.getTitulo());
		if(film.getImageUrl() != null && !film.getImageUrl().isEmpty()){
			Thread itemImageThread = new Thread(new OneArgumentRunnableObject(film.getImageUrl()) {
				
				@Override
				public void run() {
					getItemImage((String) this.getArgument());
				}
			});
			itemImageThread.start();
		}
		super.getDateLabel().setText(transmission.getFecha().toString());
		super.getCustomButton().setText("Votar");
		setAppropiateRatingImage();
	}
	
	private void setAppropiateRatingImage() {
		ImageIcon ratingImage;
		if(transmission.getRated()){
			ratingImage = new ImageIcon(getClass().getResource("/images/transmission-rated.png"));
		}else{
			ratingImage = new ImageIcon(getClass().getResource("/images/transmission-not-rated.png"));
		}
		super.getRatingImageLabel().setIcon(new UtilTools().getScaledImage(ratingImage.getImage(), 40, 40));
		//super.getRatingImageLabel().setBackground(this.getBackground());
	}

	private void getItemImage(String imageURLString) {
		URL imageURL;
		try {
			imageURL = new URL(imageURLString);
		} catch (MalformedURLException e) {
			return;
		}
		ImageIcon image = new ImageIcon(imageURL);
		//super.getItemImageLabel().setBorder(null);
		System.out.println("Ancho: " + (int)super.getItemImageLabel().getSize().width + ", Alto:" + (int)super.getItemImageLabel().getSize().getHeight());
		super.getItemImageLabel().setIcon(new UtilTools().getScaledImage(image.getImage(), 120, 120));
	}

	@Override
	protected void rateItem() {
		RateMovieView rateView = new RateMovieView(mainFrame, this, film);
		rateView.setVisible(true);
	}

	@Override
	public void filmSuccesfullyRated(FichaPelicula film) {
		transmission.setRated(true);
		setAppropiateRatingImage();
	}

}
