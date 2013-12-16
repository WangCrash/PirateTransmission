package Connection;

import java.util.List;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Codification.Base64;


public class ConnectionManager {
	private final int NO_INTERNET_REACHABILITY = 0;
	public static final String METHOD_GET = "GET";
	public static final String METHOD_POST = "POST";
	private final String USER_AGENT = "orphean_navigator_2.0";
	
	private List<String> cookiesList;
	private int TIMEOUT_MILLI = 1000;
	
	public ConnectionManager(){
		this(10000);
	}
	
	public ConnectionManager(int timeout){
		this.TIMEOUT_MILLI = timeout;
	}
	
	public Map<String, String> sendRequest(URL url, String method, boolean getBodyResponse, boolean watchCookies, boolean sendCookies){
		return sendRequest(url, null, false, null, method, getBodyResponse, watchCookies, sendCookies);
	}
	
	public Map<String, String> sendRequest(URL url, String parameters, boolean encodeParams, Map<String, String> httpAuth, String method, boolean getBodyResponse, boolean watchCookies, boolean sendCookies){
		String cookieChain = "";
		if(sendCookies){
			if(cookiesList != null){
				for (int i = 0; i < cookiesList.size(); i++) {
					String[] cookieParts = cookiesList.get(i).split(";");
					cookieChain += cookieParts[0] + ";";
				}
			}
		}
		
		/*if(encodeParams){
			//encodeparams pero Fa no hace esas cosas
		}*/
		HttpURLConnection.setFollowRedirects(false);
		
		HttpURLConnection con;
		try {
			con = (HttpURLConnection)url.openConnection();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		
		con.setReadTimeout(TIMEOUT_MILLI);
		
		con.setRequestProperty("User-Agent", USER_AGENT);
		con.setRequestProperty("Accept-Language", "es-ES,es;q=0.8,en;q=0.6");
		if(encodeParams){
			con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		}
		if(!cookieChain.isEmpty()){
			con.setRequestProperty("Cookie", cookieChain);
		}
		try {
			con.setRequestMethod(method);
		} catch (ProtocolException e) {
			return null;
		}
		
		if(httpAuth != null){
			if(httpAuth.containsKey("TOKEN_NAME")){
				con.setRequestProperty(httpAuth.get("TOKEN_NAME"), httpAuth.get("TOKEN_ID"));
			}

	        String authString = httpAuth.get("USER") + ":" + httpAuth.get("PASSWORD");
	        String authStringEnc = Base64.encodeBytes(authString.getBytes());
	        
	        con.setRequestProperty("Authorization", "Basic " + authStringEnc);
		}
		
		con.setUseCaches (false);
	    con.setDoInput(true);
		
		if(parameters != null){
			con.setDoOutput(true);

			try {
				con.getOutputStream().write(parameters.getBytes());
				con.getOutputStream().flush();
				con.getOutputStream().close();
			} catch (IOException e) {
				return null;
			}			
		}
		
		int responseCode;
		try {
			responseCode = con.getResponseCode();
		} catch (IOException e) {
			responseCode = NO_INTERNET_REACHABILITY;
		}
		
		Map<String, String> result = new HashMap<String, String>();
		result.put("ResponseCode", String.valueOf(responseCode));
		
		if((responseCode == HttpURLConnection.HTTP_OK) || (responseCode == HttpURLConnection.HTTP_MOVED_TEMP)){
			if(responseCode == HttpURLConnection.HTTP_MOVED_TEMP){
				result.put("Location", con.getHeaderField("Location"));
				
			}
			if((watchCookies) && con.getHeaderField("Set-Cookie") != null){
				if(cookiesList == null){
					cookiesList = new ArrayList<String>();
				}
				for (Map.Entry<String, List<String>> responseHeader : con.getHeaderFields().entrySet()){
					if(responseHeader.getKey() == null){
						continue;
					}
					List<String> value = responseHeader.getValue();
					System.out.print("\n" + responseHeader.getKey() + ": ");
					for (String valueElement : value) {
						System.out.print(valueElement);
						if(responseHeader.getKey().equals("Set-Cookie")){
							cookiesList.add(valueElement);
						}
					}
				}
			}
			
		}
		if(getBodyResponse){
			result.put("ResponseBody", (retrieveBodyResponse(con, responseCode)));
		}
		con.disconnect();
		return result;
	}
	
	private String retrieveBodyResponse(HttpURLConnection con, int responseCode){
		BufferedReader in;
		try {
			if(responseCode > 400){
				in = new BufferedReader(new InputStreamReader(con.getErrorStream()));
			}else{
				in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			}
		} catch (IOException e) {
			return null;
		}
		String inputLine;
		StringBuffer response = new StringBuffer();
 
		try {
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
		} catch (IOException e) {
			return null;
		}
		try {
			return new String(response.toString().getBytes(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return null;
		}		
	}

}
