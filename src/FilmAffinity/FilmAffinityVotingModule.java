package FilmAffinity;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import JSON.*;
import Connection.ConnectionManager;
import Model.FichaPelicula;

public class FilmAffinityVotingModule {
	
	private String urlBase;
	private ConnectionManager cm;
	
	private FilmAffinitySessionModule session;
	
	public FilmAffinityVotingModule(String urlBase, ConnectionManager cm){
		this.urlBase = urlBase;
		this.cm = cm;
		
		session = new FilmAffinitySessionModule(cm, urlBase);
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
			String mark;
			try{
				Integer.parseInt(film.getNotaUsuario());
				mark = film.getNotaUsuario();
			}catch(NumberFormatException e){
				mark = "ns";
			}
			String parameters = "id=" + idFilm + "&rating=" + mark + "&rsid=" + film.getDataUcd();
			
			Map<String, String> addHeaders = new HashMap<String, String>();
			addHeaders.put("X-Requested-With", "XMLHttpRequest");
			//Map<String, String> response = cm.sendRequest(url, parameters, true, null, ConnectionManager.METHOD_POST, true, true, true);
			Map<String, String> response = session.sendRequestKeepingSessionAlive(url, parameters, true, null, ConnectionManager.METHOD_POST, true, true, true, addHeaders);
			
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
					System.out.println("Voting Result: " + jsonResponse);
					if(jsonResponse.getInt("result") == 0){
						return true;
					}
					/*if(jsonResponse.get("result") != null){
						return true;
					}*/
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