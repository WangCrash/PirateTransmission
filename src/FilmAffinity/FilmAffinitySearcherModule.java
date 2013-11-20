package FilmAffinity;

import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Connection.ConnectionManager;
import Model.ArchivoTorrent;
import Model.FichaPelicula;

public class FilmAffinitySearcherModule {
	private boolean logged;
	private ConnectionManager cm;
	private String urlBase;
	private Map<String, String> stringsValoraciones;
	
	public FilmAffinitySearcherModule(String urlBase, boolean logged, ConnectionManager cm){
		this.urlBase = urlBase;
		this.logged = logged;
		this.cm = cm;
		stringsValoraciones = new HashMap<String, String>();
		stringsValoraciones.put("Muy mala", "1");
		stringsValoraciones.put("Mala", "2");
		stringsValoraciones.put("Floja", "3");
		stringsValoraciones.put("Regular", "4");
		stringsValoraciones.put("Pasable", "5");
		stringsValoraciones.put("Interesante", "6");
		stringsValoraciones.put("Buena", "7");
		stringsValoraciones.put("Notable", "8");
		stringsValoraciones.put("Muy buena", "9");
		stringsValoraciones.put("Excelente", "10");
	}
		
	public FichaPelicula[] searchFilm(String search) throws Exception{
		if(search.isEmpty()){
			return null;
		}
		
		FichaPelicula[] result = null;
		
        String query = "/es/search.php?stext=" + URLEncoder.encode(search, "UTF-8") + "&stype=title";
      
        URI uri = new URI("http", urlBase,  query, null);
        URL url = uri.toURL();

        Map<String, String> response;
        if(logged){
        	response = cm.sendRequest(url, null, null, ConnectionManager.METHOD_GET, true, true, true);
        }else{
        	response = cm.sendRequest(url, null, null, ConnectionManager.METHOD_GET, true, false, false);
        }
        int responseCode;
		String responseText = response.get("ResponseBody");
		
		try{
			responseCode = Integer.parseInt(response.get("ResponseCode"));
		}catch(NumberFormatException e){
			return null;
		}
		if((responseCode == HttpURLConnection.HTTP_MOVED_TEMP) && (response.containsKey("Location"))){
			url = new URL(url, response.get("Location"));
			FichaPelicula pelicula = getFilmDetails(url);
			if(pelicula != null){
				result = new FichaPelicula[]{pelicula};
			}
		}else if(responseCode == HttpURLConnection.HTTP_OK){
			result = extractFilmsArray(responseText);
		}
        return result;
	}
	
	public FichaPelicula getFilmDetails(URL filmDetailsUrl){
		Map<String, String> response = cm.sendRequest(filmDetailsUrl, null, null, ConnectionManager.METHOD_GET, true, false, true);
		int responseCode;
		try{
			responseCode = Integer.parseInt(response.get("ResponseCode"));
		}catch(NumberFormatException e){
			return null;
		}
		if(responseCode == HttpURLConnection.HTTP_OK){
			return extractFilmInfo(response.get("ResponseBody"));
		}
		return null;
	}
	
	private FichaPelicula[] extractFilmsArray(String html){
		
		FichaPelicula[] result = null;
		
		String filmsListFrameRegex = "<table id=\"main-content-table\">.*?<tbody>.*?<td>.*?<h1 id=\"main-title\">(.*?)</h1></td>.*?</tbody></table>";
		Pattern p = Pattern.compile(filmsListFrameRegex);
		Matcher m = p.matcher(html);
		if(m.find()){
			String filmsListFrame = m.group(1);
			
			String filmsListRegex = "<div class=\"movie-card movie-card-\\d*?\">(.*?)</div>";
			p = Pattern.compile(filmsListRegex);
			m = p.matcher(filmsListFrame);
			ArrayList<FichaPelicula> lista = new ArrayList<FichaPelicula>();
			while(m.find()){
				String filmCard = m.group(1);
				
				String filmPosterRegex = "<div class=\"mc-poster\">.*?<img src=\"(.*?)\".*?</a></div>";
				Pattern subP = Pattern.compile(filmPosterRegex);
				Matcher subM = subP.matcher(filmCard);
				String imagen = null;
				if(subM.find()){
					imagen = subM.group(1);
				}
				
				String titleRegex = "<div class=\"mc-info-container\"><div class=\"mc-title\"><a href=\"(.*?)\">(.*?)</a></div></div>";
				subP = Pattern.compile(titleRegex);
				subM = subP.matcher(filmCard);
				String title = null;
				String detailsLink = null;
				if(subM.find()){
					detailsLink = subM.group(1);
					title = subM.group(2);
				}else{
					continue;
				}
				FichaPelicula pelicula = new FichaPelicula();
				pelicula.setTitulo(title);
				pelicula.setFilmDetailsUrl(detailsLink);
				pelicula.setImageUrl(imagen);
				
				lista.add(pelicula);
			}
			result = Arrays.copyOf(lista.toArray(), lista.toArray().length, FichaPelicula[].class);
		}
		
		return result;
	}
	
	private FichaPelicula extractFilmInfo(String html){
		String contentRegex = "<table id=\"main-content-table\">(.*?)</table>";
		Pattern p = Pattern.compile(contentRegex);
		Matcher m = p.matcher(html);
		String content;
		if(m.find()){
			content = m.group(1);
		}else{
			return null;
		}
		
		String titleRegex = "<h1 id=\"main-title\">";
		p = Pattern.compile(titleRegex);
		m = p.matcher(content);
		FichaPelicula pelicula;
		if(m.find()){
			pelicula = new FichaPelicula();
			pelicula.setTitulo(m.group(1));
		}else{
			return null;
		}
		
		String ratingRegex = "<div id=\"right-column\">(.*?)</div>";
		p = Pattern.compile(ratingRegex);
		m = p.matcher(content);
		if(m.find()){
			pelicula = extractRatingsAndImage(m.group(1), pelicula); 
		}
		
		String dataRegex = "<div id=\"left-column\">(.*?)</div>";
		p = Pattern.compile(dataRegex);
		m = p.matcher(content);
		if(m.find()){
			pelicula = extractFilmData(content, pelicula);
		}
		
		return pelicula;
	}
	
	private FichaPelicula extractRatingsAndImage(String content, FichaPelicula ficha){
		String imageRegex = "<div id=\"movie-main-image-container\"><a.*?href=\"(.*?)\">.*?</a></div>";
		Pattern p = Pattern.compile(imageRegex);
		Matcher m = p.matcher(content);
		if(m.find()){
			String image = m.group(1);
		}
		
		String ratingsRegex = "<div id=\"rat-container\">.*?<div id=\"movie-rat-avg\".*?>(.*?)</div>.*?</div>";
		p = Pattern.compile(ratingsRegex);
		m = p.matcher(content);
		if(m.find()){
			String rating = m.group(1);
		}
		
		if(logged){
			String userRatingRegex = "<div class=\"rate-movie-box\".*?data-user-rating=\"(.*?)\".*?>";
			p = Pattern.compile(userRatingRegex);
			m = p.matcher(content);
			if(m.find()){
				String userRating = m.group(1);
			}
		}
		return ficha;
	}
	
	private FichaPelicula extractFilmData(String content, FichaPelicula ficha){
		String dataFrameRegex = "<dl class=\"movie-info\">(.*?)</dl>";
		Pattern p = Pattern.compile(dataFrameRegex);
		Matcher m = p.matcher(content);
		if(m.find()){
			
			String dataFrame = m.group(1);
			
			String dataContentRegex = "<dt>(.*?)</dt>";
			p = Pattern.compile(dataContentRegex);
			m = p.matcher(dataFrame);
			while(m.find()){
				String label = m.group(1);
				String data = m.group(2);
			}
		}
		
		String reviewsFrameRegex = "<ul id=\"pro-reviews\">(.*?)</dt>";
		p = Pattern.compile(reviewsFrameRegex);
		m = p.matcher(content);
		if(m.find()){
			String reviewsFrame = m.group(1);
			
			String reviewsRegex = "<li><div class=\"pro-review\"><div>\"(.*?)\"</div><div class=\"pro-crit-med\">(.*?)</div></div></li>";
			p = Pattern.compile(reviewsRegex);
			m = p.matcher(reviewsFrame);
			while(m.find()){
				String review = m.group(1);
				String authorAndCalibration = m.group(2);
			}
		}
		
		String almasGemelasRegex = "<div style.*?>\"La votación media de tus almas gemelas es \"<b>(.*?)</b>";
		p = Pattern.compile(almasGemelasRegex);
		m = p.matcher(content);
		if(m.find()){
			String notaAlmasGemelas = m.group(1);
		}
		
		return ficha;
	}
	
	private FichaPelicula fillFichaPelicula(String field, String data, FichaPelicula ficha){
		//if(field.equals("Titulo")){
			//ficha.setTitulo;
		//}
		return ficha;
	}
}
