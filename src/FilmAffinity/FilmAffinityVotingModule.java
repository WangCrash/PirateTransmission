package FilmAffinity;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import JSON.*;

import Connection.ConnectionManager;
import Model.FichaPelicula;

public class FilmAffinityVotingModule {
	
	private String urlBase;
	private ConnectionManager cm;
	
	public FilmAffinityVotingModule(String urlBase, ConnectionManager cm){
		this.urlBase = urlBase;
		this.cm = cm;
	}
	
	public boolean voteForFilm(FichaPelicula film){
		if(film.getNotaUsuario() == null || film.getNotaUsuario().isEmpty()){
			return false;
		}
		String idFilm = this.getFilmIdFromUrl(film.getFilmDetailsUrl());
		if((idFilm != null) && film.getDataUcd() != null){
			URL url;
			try {
				url = new URL(new URL("http://" + urlBase), "/es/ratingajax.php");
			} catch (MalformedURLException e) {
				return false;
			}
			String parameters = "id=" + idFilm + "&rating=" + film.getNotaUsuario() + "&ucd=" + film.getDataUcd();
			
			Map<String, String> response = cm.sendRequest(url, parameters, true, null, ConnectionManager.METHOD_POST, true, true, true);
			
			if(response != null){
				int responseCode;
				try{
					responseCode = Integer.parseInt(response.get("ResponseCode"));
				}catch(NumberFormatException e){
					return false;
				}
				if(responseCode == 200){
					JSONObject jsonResponse = new JSONObject(response.get("ResponseBody"));
					System.out.println(jsonResponse.getInt("result"));
					if(jsonResponse.getInt("result") == 0){
						return true;
					}
				}
			}
		}
		return false;
	}
	
	private String getFilmIdFromUrl(String filmUrl){
		String idRegex = "film(\\d*?).html";
		Pattern p = Pattern.compile(idRegex);
		Matcher m = p.matcher(filmUrl);
		if(m.find()){
			return m.group(1);
		}
		return null;
	}
}