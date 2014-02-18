package GUI.Transmisiones;

import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import GUI.Helpers.Results.Items.Films.Rating.RateFilm;
import GUI.Helpers.Results.Items.Films.Rating.RateMovieView;
import Model.FichaPelicula;
import Model.Transmision;
import Utils.OneArgumentRunnableObject;
import Utils.UtilTools;

@SuppressWarnings("serial")
public class FilmTransmisionCell extends TransmisionCell implements RateFilm{
	private FichaPelicula film;

	public FilmTransmisionCell(){
		super();
	}
	/**
	 * Create the panel.
	 */
	public FilmTransmisionCell(JFrame mainFrame, TransmisionesView parentView, Transmision transmission) {
		super(mainFrame, parentView, transmission);
		this.film = (FichaPelicula)transmission.getHelperItem();
		initLabels();
	}

	@Override
	protected void initLabels() {
		super.getItemTypeLabel().setText("PELÍCULA/SERIE/DOCUMENTAL");
		super.getTitleLabel().setText(film.getTitulo());
		super.getTitleLabel().setToolTipText(film.getTitulo());
		getCustomButton().setIcon(new ImageIcon(TransmisionCell.class.getResource("/images/HelperResults/rate-film-icon.png")));
		getCustomButton().setToolTipText("Votar");
		if(film.getImageUrl() != null && !film.getImageUrl().isEmpty()){
			Thread itemImageThread = new Thread(new OneArgumentRunnableObject(film.getImageUrl()) {
				
				@Override
				public void run() {
					getItemImage((String) this.getArgument());
				}
			});
			itemImageThread.start();
		}
		setAppropiateRatingImage();
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
		this.film = film;
		setAppropiateRatingImage();
		updateTransmission();
	}

}
