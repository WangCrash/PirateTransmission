package FilmAffinity;

import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
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
        URL url = new URL(new URL("http://" + urlBase), query);

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
		
		String titleRegex = "<h1 id=\"main-title\">.*?<span itemprop=\"name\">(.*?)</span>.*?</h1>";
		p = Pattern.compile(titleRegex);
		m = p.matcher(content);
		FichaPelicula pelicula;
		if(m.find()){
			pelicula = new FichaPelicula();
			pelicula.setTitulo(m.group(1));
		}else{
			return null;
		}
		
		String ratingRegex = "<div id=\"right-column\">(.*?)</div>.*?<div id=\"left-column\">";
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
		System.out.println(content);
		String imageRegex = "<div id=\"movie-main-image-container\">.*?<img itemprop=\"image\".*src=\"(.*?)\">.*?</div>";
		Pattern p = Pattern.compile(imageRegex);
		Matcher m = p.matcher(content);
		if(m.find()){
			String image = m.group(1);
			ficha.setImageUrl(image);
		}
		
		String ratingsRegex = "<div id=\"rat-container\">.*?<div id=\"movie-rat-avg\".*?>(.*?)</div>.*?</div>";
		p = Pattern.compile(ratingsRegex);
		m = p.matcher(content);
		if(m.find()){
			String rating = m.group(1);
			ficha.setValoracion(rating);
		}
		
		if(logged){
			String userRatingRegex = "<div class=\"rate-movie-box\".*?data-user-rating=\"(.*?)\".*?>";
			p = Pattern.compile(userRatingRegex);
			m = p.matcher(content);
			if(m.find()){
				String userRating = m.group(1);
				ficha.setNotaUsuario(userRating);
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
			
			String dataContentRegex = "<dt>(.*?)</dt><dd>(.*?)</dd>";
			p = Pattern.compile(dataContentRegex);
			m = p.matcher(dataFrame);
			while(m.find()){
				String label = m.group(1);
				String data = m.group(2);
				if(label.equals("Sinopsis")){
					String sinopsisRegex = "\"(.*?)\"";
					Pattern subP = Pattern.compile(sinopsisRegex);
					Matcher subM = subP.matcher(data);
					if(m.find()){
						data = subM.group(1);
					}
				}
				this.fillFichaPelicula(label, data, ficha);
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
			Map<String, String[]> criticas = new HashMap<String, String[]>();
			while(m.find()){
				String review = m.group(1);
				review = review.replaceAll("\"", "");
				String authorAndCalibrationContent = m.group(2);
				String calibrationRegex = "\"(.*?)\"<img.*?title=\"(.*?)\".*?>";
				Pattern subP = Pattern.compile(calibrationRegex);
				Matcher subM = subP.matcher(authorAndCalibrationContent);
				String[] reviewAndCalibration = new String[2];
				String author = "";
				reviewAndCalibration[0] = review;
				if(subM.find()){
					author = m.group(1);
					reviewAndCalibration[1] = m.group(2);
				}
				criticas.put(author, reviewAndCalibration);
			}
		}
		
		String almasGemelasRegex = "<div style.*?>\"La votación media de tus almas gemelas es \"<b>(.*?)</b>";
		p = Pattern.compile(almasGemelasRegex);
		m = p.matcher(content);
		if(m.find()){
			String notaAlmasGemelas = m.group(1);
			ficha.setNotaAlmasGemelas(notaAlmasGemelas);
		}
		
		return ficha;
	}
	
	private FichaPelicula fillFichaPelicula(String field, String data, FichaPelicula ficha){
		String value;
		try {
			value = new String(data.getBytes(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			value = data;
		}
		if(field.equals("Título original")){
			ficha.setTituloOriginal(value);
		}else if(field.equals("Año")){
			ficha.setAño(value);
		}else if(field.equals("Duración")){
			ficha.setDuracion(value);
		}else if(field.equals("País")){
			ficha.setPais(value);
		}else if(field.equals("Director")){
			ficha.setDirector(this.getListFromValue(value));
		}else if(field.equals("Guión")){
			ficha.setGuion(value);
		}else if(field.equals("Música")){
			ficha.setMusica(value);
		}else if(field.equals("Fotografía")){
			ficha.setFotografia(value);
		}else if(field.equals("Reparto")){
			ficha.setReparto(this.getListFromValue(value));
		}else if(field.equals("Productora")){
			ficha.setProductora(value);
		}else if(field.equals("Género")){
			String[] generos = this.getListFromValue(value);
			String genero = "";
			for (int i = 0; i < generos.length; i++) {
				genero += generos[i] + " ";
			}
			genero = genero.trim();
			ficha.setGenero(genero);
		}else if(field.equals("Sinopsis")){
			ficha.setSinopsis(value);
		}
		
		return ficha;
	}
	
	private String[] getListFromValue(String value){
		String listComponentsRegex = "<a href=.*?>(.*?)</a>";
		Pattern p = Pattern.compile(listComponentsRegex);
		Matcher m = p.matcher(value);
		ArrayList<String> lista = new ArrayList<String>();
		while(m.find()){
			lista.add(m.group(1));
		}
		return Arrays.copyOf(lista.toArray(), lista.size(), String[].class);
	}
	
	public static void main(String[] args){
		FilmAffinitySearcherModule f = new FilmAffinitySearcherModule("http://localhost:8080", false, new ConnectionManager());
		URL url;
		try {
			url = new URL(f.urlBase);
		} catch (MalformedURLException e) {
			return;
		};
		f.getFilmDetails(url);
	}
}
