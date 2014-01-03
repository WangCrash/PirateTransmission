package FilmAffinity;

import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
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
import Utils.UtilTools;

public class FilmAffinitySearcherModule {
		
	private boolean logged;
	private ConnectionManager cm;
	private String urlBase;
	private Map<String, String> stringsValoraciones;
	private String[] genresKeys;
	private String[] searchingOptionsKeys;
	
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
		
		genresKeys = new String[]{"AC", "AN", "AV", "BE", "C-F", "F-N", "CO", "DESC", "DO", "DR", "FAN", "INF", "INT", "MU", "RO", "TV_SE", "TE", "TH", "WE"};
		searchingOptionsKeys = new String[]{"all", "title", "director", "cast"};
	}
	
	public FichaPelicula[] lookForRecommendations(){
		return lookForRecommendations(null);
	}
	
	public String getGenreKey(int genre){
		if((genre >= 0) && (genre < this.genresKeys.length)){
			return genresKeys[genre];
		}else if(genre == -1){
			return "";
		}
		return null;
	}
	
	public FichaPelicula completeFilmDetails(FichaPelicula film){
		if((film.getFilmDetailsUrl() == null) || (film.getFilmDetailsUrl().isEmpty())){
			return film;
		}
		URL urlFilm;
		try {
			urlFilm = new URL(new URL("http://" + this.urlBase), film.getFilmDetailsUrl());
		} catch (MalformedURLException e) {
			return film;
		}
		return getFilmDetails(urlFilm);
	}
	
	public FichaPelicula[] lookForRecommendations(Map<String, String> filters){
		FichaPelicula[] result = null;
		
		String query = "/es/smsrec.php";
		if(filters != null){
			query += "?";
			for (Map.Entry<String, String> entry : filters.entrySet())
			{
			    query += entry.getKey() + "=" + entry.getValue() + "&";
			}
			query = query.substring(0, query.length() - 1);
		}
        URL url;
		try {
			url = new URL(new URL("http://" + urlBase), query);
			//url = new URL(urlBase);
		} catch (MalformedURLException e1) {
			return null;
		}

        Map<String, String> response;
        response = cm.sendRequest(url, ConnectionManager.METHOD_GET, true, true, true);
        
        int responseCode;
		try{
			responseCode = Integer.parseInt(response.get("ResponseCode"));
		}catch(NumberFormatException e){
			return null;
		}
		if(responseCode == 0){
			System.out.println("Error: no connection");
			return null;
		}
		String responseText = response.get("ResponseBody");
		if(responseText == null){
			return null;
		}
		if(responseCode == HttpURLConnection.HTTP_OK){
			ArrayList<FichaPelicula> lista = new ArrayList<FichaPelicula>();
			lista = extractFilmsArray(responseText, lista);
			result = Arrays.copyOf(lista.toArray(), lista.size(), FichaPelicula[].class);
		}
		
		return result;
	}
		
	public FichaPelicula[] searchFilm(String search, int option){
		if(search.isEmpty()){
			return null;
		}
		
		FichaPelicula[] result = null;
		
        String query = "/es/search.php?stext=" + new UtilTools().encodeString(search) + "&stype=" + searchingOptionsKeys[option];
        URL url;
		try {
			url = new URL(new URL("http://" + urlBase), query);
			//url = new URL(urlBase);
		} catch (MalformedURLException e1) {
			return null;
		}

        Map<String, String> response;
        if(logged){
        	response = cm.sendRequest(url, ConnectionManager.METHOD_GET, true, true, true);
        }else{
        	response = cm.sendRequest(url, ConnectionManager.METHOD_GET, true, false, false);
        }
        int responseCode;
		try{
			responseCode = Integer.parseInt(response.get("ResponseCode"));
		}catch(NumberFormatException e){
			return null;
		}
		if(responseCode == 0){
			System.out.println("Error: no connection");
			return null;
		}
		
		String responseText = response.get("ResponseBody");
		if(responseText == null){
			return null;
		}
		
		if((responseCode == HttpURLConnection.HTTP_MOVED_TEMP) && (response.containsKey("Location"))){
			try {
				url = new URL(url, response.get("Location"));
			} catch (MalformedURLException e) {
				return null;
			}
			FichaPelicula pelicula = getFilmDetails(url);
			if(pelicula != null){
				result = new FichaPelicula[]{pelicula};
			}
		}else if(responseCode == HttpURLConnection.HTTP_OK){
			result = extractMovies(search, responseText);
		}
        return result;
	}
	
	public FichaPelicula getFilmDetails(URL filmDetailsUrl){
		Map<String, String> response = cm.sendRequest(filmDetailsUrl, ConnectionManager.METHOD_GET, true, false, true);
		int responseCode;
		try{
			responseCode = Integer.parseInt(response.get("ResponseCode"));
		}catch(NumberFormatException e){
			return null;
		}
		if(responseCode == HttpURLConnection.HTTP_OK){
			FichaPelicula pelicula = extractFilmInfo(response.get("ResponseBody"));
			if(pelicula != null){
				pelicula.setFilmDetailsUrl(filmDetailsUrl.getPath());
			}
			return pelicula;
		}
		return null;
	}
	
	private FichaPelicula[] extractMovies(String search, String html){
		String numeroResultadosRegex = "<div style=\"text-align:right;\">\\s*?<strong>(\\d*?)</strong>.*?resultados";
		Pattern p = Pattern.compile(numeroResultadosRegex);
		Matcher m = p.matcher(html);
		int numResultados = 0;
		if(m.find()){
			try{
				numResultados = Integer.parseInt(m.group(1));
			}catch(NumberFormatException e){
				return null;//no hay resultados en teoría
			}
		}
		ArrayList<FichaPelicula> lista = new ArrayList<FichaPelicula>();
		int paginas = numResultados / 50;
		if(numResultados % 50 == 0){
			paginas--;
		}
		lista = extractAllPages(search, html, paginas, 0, lista);
		return Arrays.copyOf(lista.toArray(), lista.toArray().length, FichaPelicula[].class);
	}
	
	private ArrayList<FichaPelicula> extractAllPages(String search, String html, int numPaginas, int pagina, ArrayList<FichaPelicula> lista){
		lista = extractFilmsArray(html, lista);
		if(pagina == numPaginas){
			return lista;
		}else{
			String query;
			try {
				query = "/es/search.php?stext=" + URLEncoder.encode(search, "UTF-8") + "&stype=title&from=" + (50 * (pagina + 1));
			} catch (UnsupportedEncodingException e1) {
				return lista;
			}
		    URL url;
			try {
				url = new URL(new URL("http://" + urlBase), query);
			} catch (MalformedURLException e1) {
				return lista;
			}
		    Map<String, String> response;
		    if(logged){
	        	response = cm.sendRequest(url, ConnectionManager.METHOD_GET, true, true, true);
	        }else{
	        	response = cm.sendRequest(url, ConnectionManager.METHOD_GET, true, false, false);
	        }
		    int responseCode;
			String responseText = response.get("ResponseBody");
			
			try{
				responseCode = Integer.parseInt(response.get("ResponseCode"));
			}catch(NumberFormatException e){
				return null;
			}
			if(responseCode == HttpURLConnection.HTTP_OK){
				return extractAllPages(search, responseText, numPaginas, pagina + 1, lista);
			}else{
				return lista;
			}
		}
	}
	
	private ArrayList<FichaPelicula> extractFilmsArray(String html, ArrayList<FichaPelicula> lista){
		
		String filmsListRegex = "<div class=\"movie-card movie-card-\\d*?\">(.*?<div class=\"mc-poster\">.*?<div class=\"mc-info-container\">.*?<img.*?countries.*?)</div>\\s*?</div>";
		Pattern p = Pattern.compile(filmsListRegex);
		Matcher m = p.matcher(html);
		while(m.find()){
			String filmCard = m.group(1);
			System.out.println(filmCard);
			String filmPosterRegex = "<div class=\"mc-poster\">.*?<img src=\"(.*?)\".*?</a>";
			Pattern subP = Pattern.compile(filmPosterRegex);
			Matcher subM = subP.matcher(filmCard);
			String imagen = null;
			if(subM.find()){
				imagen = subM.group(1);
			}
			
			//String titleRegex = "<div class=\"mc-info-container\">.*?<div class=\"mc-title\">\\s*?<a href=\"(.*?)\">(.*?)</a>";
			String titleRegex = "<div class=\"mc-info-container\">.*?<div class=\"mc-title\">\\s*?<a href=\"(.*?)\">(.*?)</a>\\s*?\\((.*?)\\)\\s*?<img.*?countries.*?title=\"(.*?)\".*?>";
			subP = Pattern.compile(titleRegex);
			subM = subP.matcher(filmCard);
			String title = null;
			String detailsLink = null;
			String year = null;
			String country = null;
			if(subM.find()){
				detailsLink = subM.group(1);
				title = subM.group(2);
				year = subM.group(3);
				country = subM.group(4);
			}else{
				continue;
			}
			FichaPelicula pelicula = new FichaPelicula();
			pelicula.setTitulo(new UtilTools().escapeHtmlSpecialChars(title));
			pelicula.setFilmDetailsUrl(detailsLink);
			pelicula.setImageUrl(imagen);
			pelicula.setAño(year);
			pelicula.setPais(country);
			
			lista.add(pelicula);
		}
		
		return lista;
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
		
		String ratingRegex = "<div id=\"right-column\">(.*?)</div>\\s*?<div id=\"left-column\">";
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
		String imageRegex = "<div id=\"movie-main-image-container\">.*?<img itemprop=\"image\".*src=\"(.*?)\">\\s*?</a>";
		Pattern p = Pattern.compile(imageRegex);
		Matcher m = p.matcher(content);
		if(m.find()){
			String image = m.group(1);
			ficha.setImageUrl(image);
		}
		
		String ratingsRegex = "<div id=\"rat-container\">.*?<div id=\"movie-rat-avg\" itemprop=\"ratingValue\">\\s*?(.*?)\\s*?</div>";
		p = Pattern.compile(ratingsRegex);
		m = p.matcher(content);
		if(m.find()){
			String rating = m.group(1);
			ficha.setValoracion(rating.trim());
		}
		
		if(logged){
			String userRatingRegex = "<div class=\"rate-movie-box\".*?data-user-rating=\"(.*?)\"\\s*?data-ucd=\"(.*?)\".*?>";
			p = Pattern.compile(userRatingRegex);
			m = p.matcher(content);
			if(m.find()){
				String userRating = m.group(1);
				String dataUcd = m.group(2);
				ficha.setNotaUsuario(userRating.trim());
				ficha.setDataUcd(dataUcd);
			}
		}
		return ficha;
	}
	
	private FichaPelicula extractFilmData(String content, FichaPelicula ficha){
		System.out.println(content);
		String dataFrameRegex = "<dl class=\"movie-info\">(.*?)</dl>";
		Pattern p = Pattern.compile(dataFrameRegex);
		Matcher m = p.matcher(content);		
		if(m.find()){
			
			String dataFrame = m.group(1);
			
			String dataContentRegex = "<dt>(.*?)</dt>\\s*?<dd>(.*?)</dd>";
			p = Pattern.compile(dataContentRegex);
			m = p.matcher(dataFrame);
			while(m.find()){
				String label = m.group(1);
				String data = m.group(2);
				if(label.equals("Sinopsis")){
					String sinopsisRegex = "\"(.*?)\"";
					Pattern subP = Pattern.compile(sinopsisRegex);
					Matcher subM = subP.matcher(data);
					if(subM.find()){
						data = subM.group(1);
					}
				}else if(label.equals("Pa&iacute;s")){
					String paisRegex = "<img.*?title=\"(.*?)\".*?>";
					Pattern subP = Pattern.compile(paisRegex);
					Matcher subM = subP.matcher(data);
					if(subM.find()){
						data = subM.group(1);
					}
				}
				this.fillFichaPelicula(label, data, ficha);
			}
		}
		
		String awardsFrameRegex = "<dt>\\s*?Premios\\s*?</dt>\\s*?<dd class=\"award\">(.*?)</dd>";
		p = Pattern.compile(awardsFrameRegex);
		m = p.matcher(content);
		if(m.find()){
			String awardsFrame = m.group(1);
			
			String awardsRegex = "<div.*?>\\s*?<a href.*?>(.*?)</a>(.*?)</div>";
			p = Pattern.compile(awardsRegex);
			m = p.matcher(awardsFrame);
			ArrayList<String> list = new ArrayList<String>();
			while(m.find()){
				String year = m.group(1);
				String award = m.group(2).trim();
				list.add(year + award);
			}
			ficha.setPremios(Arrays.copyOf(list.toArray(), list.size(), String[].class));
		}
		
		String reviewsFrameRegex = "<ul id=\"pro-reviews\">(.*?)\\s*?</ul>";
		p = Pattern.compile(reviewsFrameRegex);
		m = p.matcher(content);
		if(m.find()){
			String reviewsFrame = m.group(1);
			System.out.println(reviewsFrame);
			
			String reviewsRegex = "<li\\s*?>\\s*?<div class=\"pro-review\">.*?<div>\\s*?(.*?)\\s*?</div>.*?<div class=\"pro-crit-med\">\\s*?(.*?)\\s*?</div>\\s*?</div>\\s*?</li>";
			p = Pattern.compile(reviewsRegex);
			m = p.matcher(reviewsFrame);
			Map<String, String[]> criticas = new HashMap<String, String[]>();
			while(m.find()){
				String review = m.group(1).trim();
				review = new UtilTools().quitQuotes(review);
				
				String authorAndCalibrationContent = m.group(2);
				String calibrationRegex = "(.*?)<img.*?title='(.*?)'.*?>";
				Pattern subP = Pattern.compile(calibrationRegex);
				Matcher subM = subP.matcher(authorAndCalibrationContent);
				String[] reviewAndCalibration = new String[2];
				String author = "";
				reviewAndCalibration[0] = review;
				if(subM.find()){
					author = subM.group(1);
					reviewAndCalibration[1] = new UtilTools().escapeHtmlSpecialChars(subM.group(2));
				}
				criticas.put(author, reviewAndCalibration);
			}
			ficha.setCriticas(criticas);
		}
		
		String almasGemelasRegex = "La votaci&oacute;n media de tus almas gemelas es <b>(.*?)</b>";
		p = Pattern.compile(almasGemelasRegex);
		m = p.matcher(content);
		if(m.find()){
			String notaAlmasGemelas = m.group(1);
			ficha.setNotaAlmasGemelas(notaAlmasGemelas);
		}
		
		return ficha;
	}
	
	private FichaPelicula fillFichaPelicula(String field, String data, FichaPelicula ficha){
		String value = new UtilTools().escapeHtmlSpecialChars(data);
		if(field.equals("T&iacute;tulo original")){//Título original
			ficha.setTituloOriginal(value);
		}else if(field.equals("A&ntilde;o")){//Año
			ficha.setAño(value);
		}else if(field.equals("Duraci&oacute;n")){//Duración
			ficha.setDuracion(value);
		}else if(field.equals("Pa&iacute;s")){//País
			ficha.setPais(value);
		}else if(field.equals("Director")){
			ficha.setDirector(this.getListFromValue(value));
		}else if(field.equals("Gui&oacute;n")){//Guión
			ficha.setGuion(value);
		}else if(field.equals("M&uacute;sica")){//Música
			ficha.setMusica(value);
		}else if(field.equals("Fotograf&iacute;a")){//Fotografía
			ficha.setFotografia(value);
		}else if(field.equals("Reparto")){
			ficha.setReparto(this.getListFromValue(value));
		}else if(field.equals("Productora")){
			ficha.setProductora(value);
		}else if(field.equals("G&eacute;nero")){//Género
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
	
	public static void main(String[] args) throws MalformedURLException{
		FilmAffinitySearcherModule f = new FilmAffinitySearcherModule("http://localhost:8080", true, new ConnectionManager());
		//"http://www.filmaffinity.com/es/search.php?stype=title&stext=batman"
		/*Map<String, String> filters = new HashMap<String, String>();
		filters.put("genre", "BE");
		filters.put("limit", "50");
		filters.put("fromyear", "1980");
		filters.put("toyear", "2020");
		FichaPelicula[] result = f.lookForRecommendations(filters);*/
		FichaPelicula result = f.getFilmDetails(new URL("http://localhost:8080"));
		if(result == null){
			return;
		}
		System.out.println(result);
		/*for (int i = 0; i < result.length; i++) {
			System.out.println(result[i]);
		}*/
	}
}
