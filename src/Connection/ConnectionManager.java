package Connection;

import java.util.List;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Codification.Base64;


public class ConnectionManager {
	
	public static final String METHOD_GET = "GET";
	public static final String METHOD_POST = "POST";
	
	public static final String TOKEN_NAME_BASIC_AUTH_KEY = "TOKEN_NAME";
	public static final String TOKEN_ID_BASIC_AUTH_KEY = "TOKEN_ID";
	public static final String USER_BASIC_AUTH_KEY = "USER";
	public static final String PASSWORD_BASIC_AUTH_KEY = "PASSWORD";
	
	public static final String BODY_TEXT_RESPONSE_KEY = "ResponseBody";
	public static final String STATUS_CODE_RESPONSE_KEY = "ResponseCode";
	
	private final int NO_INTERNET_REACHABILITY = 0;
	public static final String USER_AGENT = "orphean_navigator_2.0";
	
	private List<String> cookiesList;
	private Map<String, String> responseHeaders;
	private int TIMEOUT_MILLI = 10000;
	
	public ConnectionManager(){
		this(10000);
	}
	
	public ConnectionManager(int timeout){
		this.TIMEOUT_MILLI = timeout;
	}
	
	public Map<String, String> sendRequest(URL url, String method, boolean getBodyResponse, boolean watchCookies, boolean sendCookies){
		return sendRequest(url, null, false, null, method, getBodyResponse, watchCookies, sendCookies, null);
	}
	
	public Map<String, String> sendRequest(URL url, String parameters, boolean encodeParams, Map<String, String> httpAuth, String method, boolean getBodyResponse, boolean watchCookies, boolean sendCookies){
		return sendRequest(url, parameters, encodeParams, httpAuth, method, getBodyResponse, watchCookies, sendCookies, null);
	}
	
	public Map<String, String> sendRequest(URL url, String parameters, boolean encodeParams, Map<String, String> httpAuth, String method, boolean getBodyResponse, boolean watchCookies, boolean sendCookies, Map<String, String> addHeaders){
		String cookieChain = "";
		if(sendCookies){
			if(cookiesList != null){
				for (int i = 0; i < cookiesList.size(); i++) {
					String[] cookieParts = cookiesList.get(i).split(";");
					cookieChain += cookieParts[0] + ";";
				}
				cookieChain = cookieChain.substring(0, cookieChain.length() - 1);
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
		
		if(addHeaders != null){
			for (Map.Entry<String, String> newHeader : addHeaders.entrySet()){
				con.setRequestProperty(newHeader.getKey(), newHeader.getValue());
			}
		}
		
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
			if(httpAuth.containsKey(TOKEN_NAME_BASIC_AUTH_KEY)){
				con.setRequestProperty(httpAuth.get(TOKEN_NAME_BASIC_AUTH_KEY), httpAuth.get(TOKEN_ID_BASIC_AUTH_KEY));
			}

	        String authString = httpAuth.get(USER_BASIC_AUTH_KEY) + ":" + httpAuth.get(PASSWORD_BASIC_AUTH_KEY);
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
		result.put(STATUS_CODE_RESPONSE_KEY, String.valueOf(responseCode));
		
		catchHeadersResponse(con);
		
		if((responseCode == HttpURLConnection.HTTP_OK) || (responseCode == HttpURLConnection.HTTP_MOVED_TEMP) || responseCode == HttpURLConnection.HTTP_MOVED_PERM){
			if(responseCode == HttpURLConnection.HTTP_MOVED_TEMP || responseCode == HttpURLConnection.HTTP_MOVED_PERM){
				result.put("Location", con.getHeaderField("Location"));
			}
			if((watchCookies) && con.getHeaderField("Set-Cookie") != null){
				if(cookiesList != null){
					cookiesList.clear();
				}else{
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
			result.put(BODY_TEXT_RESPONSE_KEY, (retrieveBodyResponse(con, responseCode)));
		}
		con.disconnect();
		return result;
	}

	private String retrieveBodyResponse(HttpURLConnection con, int responseCode){
		BufferedReader in;
		try {
			if(responseCode >= 400){
				try{
					in = new BufferedReader(new InputStreamReader(con.getErrorStream()));
				}catch(NullPointerException e){
					try{
						in = new BufferedReader(new InputStreamReader(con.getInputStream()));
					}catch(NullPointerException e2){
						return null;
					}
				}
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
			return new String(response.toString().getBytes(), "ISO-8859-1");
		} catch (UnsupportedEncodingException e) {
			return null;
		}		
	}
	
	private void catchHeadersResponse(HttpURLConnection con){
		responseHeaders = new HashMap<String, String>();
		for (Map.Entry<String, List<String>> responseHeader : con.getHeaderFields().entrySet()){
			if(responseHeader.getKey() == null || responseHeader.getKey().equals("Set-Cookie")){
				continue;
			}
			List<String> value = responseHeader.getValue();
			int i = 0;
			for (String valueElement : value) {
				if(i == 0){
					responseHeaders.put(responseHeader.getKey(), valueElement);
				}else{
					responseHeaders.put(responseHeader.getKey(), responseHeaders.get(responseHeader.getKey()) + ";" + valueElement);
				}
				i++;
			}
		}
	}
	
	public Map<String, String> getResonseHeaders() {
		return responseHeaders;
	}

	public void setResonseHeaders(Map<String, String> resonseHeaders) {
		this.responseHeaders = resonseHeaders;
	}

	public static void main(String[] args){
		URL url;
		try {
			url = new URL("http://thepiratebay.org");
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return;
		}
		ConnectionManager cm = new ConnectionManager();
		Map<String, String> response = cm.sendRequest(url, ConnectionManager.METHOD_GET, true, false, false);
		System.out.println(response);
	}
}
