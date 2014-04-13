package Connection;
import java.net.URL;
import java.util.Map;

public class SimpleConnectionManager extends ConnectionManager{
	
	public SimpleConnectionManager(){
		super();
	}
	
	public SimpleConnectionManager(int timeout){
		super(timeout);
	}
	
	public Map<String, String> sendPostRequest(URL url){
		return this.sendPostRequest(url, null, null);
	}
	
	public Map<String , String> sendPostRequest(URL url, String parameters){
		return this.sendPostRequest(url, parameters, null);
	}
	
	public Map<String, String> sendPostRequest(URL url, String parameters, Map<String, String> httpAuth){
		return this.sendRequest(url, parameters, false, httpAuth, METHOD_POST, true, false, false, null);
	}
	
	public Map<String, String> sendGetRequest(URL url){
		return this.sendGetRequest(url, null, null);
	}
	
	public Map<String, String> sendGetRequest(URL url, String parameters){
		return this.sendGetRequest(url, parameters, null);
	}
	
	public Map<String, String> sendGetRequest(URL url, String parameters, Map<String, String> httpAuth){
		return this.sendRequest(url, parameters, false, httpAuth, METHOD_GET, true, false, false, null);
	}
}
