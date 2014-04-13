package FilmAffinity;

import java.net.URL;
import java.util.Map;

import Connection.ConnectionManager;
import Managers.Helpers.FilmAffinityBot;

public class FilmAffinitySessionModule {
	ConnectionManager cm;
	String urlBase;
	String user;
	String pass;
	
	public FilmAffinitySessionModule(ConnectionManager cm, String urlBase){
		this.cm = cm;
		this.user = FilmAffinityBot.getInstance().getUser();
		this.pass = FilmAffinityBot.getInstance().getPassword();
		this.urlBase = urlBase;
	}
	public Map<String, String> sendRequestKeepingSessionAlive(URL url, String method, boolean getBodyResponse, boolean watchCookies, boolean sendCookies){
		return sendRequestKeepingSessionAlive(url, null, false, null, method, getBodyResponse, watchCookies, sendCookies);
	}
	
	public Map<String, String> sendRequestKeepingSessionAlive(URL url, String parameters, boolean encodeParams, Map<String, String> httpAuth, String method,
			boolean getBodyResponse, boolean watchCookies, boolean sendCookies){
		return sendRequestKeepingSessionAlive(url, parameters, encodeParams, httpAuth, method, getBodyResponse, watchCookies, sendCookies, null);
	}
	
	public Map<String, String> sendRequestKeepingSessionAlive(URL url, String parameters, boolean encodeParams, Map<String, String> httpAuth, String method,
			boolean getBodyResponse, boolean watchCookies, boolean sendCookies, Map<String , String> addHeaders){
		
		Map<String, String> response = cm.sendRequest(url, parameters, encodeParams, httpAuth, method, getBodyResponse, watchCookies, sendCookies, addHeaders);
		boolean doRelogin = false;
		if(FilmAffinityBot.getInstance().isLogged()){
			if(response.get(ConnectionManager.STATUS_CODE_RESPONSE_KEY).equals("200")
						|| response.get(ConnectionManager.STATUS_CODE_RESPONSE_KEY).equals("301")
						|| response.get(ConnectionManager.STATUS_CODE_RESPONSE_KEY).equals("302")){
				//si el servidor no devuelve la header Expires se da por hecho que no hay sesión.
				if(!cm.getResonseHeaders().containsKey("Expires")){
					doRelogin = true;
				}
			}
		}
		if(doRelogin){
			if(new FilmAffinityLoginModule(cm, urlBase).login(user, pass)){
				System.out.println("logged");
				return sendRequestKeepingSessionAlive(url, parameters, encodeParams, httpAuth, method, getBodyResponse, watchCookies, sendCookies);
			}else{
				System.out.println("ERROR: couldn't login");
			}
		}
		return response;
	}
}
