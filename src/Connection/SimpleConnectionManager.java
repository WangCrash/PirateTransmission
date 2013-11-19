package Connection;
import java.net.URL;
import java.util.Map;

public class SimpleConnectionManager{
	
	public static Map<String, String> sendPostRequest(URL url, String parameters, Map<String, String> httpAuth){
		return ConnectionManager.sendRequest(url, parameters, httpAuth, ConnectionManager.METHOD_POST, true, false, false);
	}
	public static Map<String, String> sendGetRequest(URL url, String parameters, Map<String, String> httpAuth){
		return ConnectionManager.sendRequest(url, parameters, httpAuth, ConnectionManager.METHOD_GET, true, false, false);
	}
}
