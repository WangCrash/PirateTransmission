import java.util.List;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class ConnectionManager {
	private static String USER_AGENT = "orphean_navigator_2.0";
	private static List<String> cookiesList;
	
	private static final int NO_INTERNET_REACHABILITY = 0;
	private static final int TIMEOUT_MILLI = 10000;
	public static final String METHOD_GET = "GET";
	public static final String METHOD_POST = "POST";
	
	public static String[] sendGetRequest(URL url) throws Exception{
		
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		
		con.setConnectTimeout(TIMEOUT_MILLI);
		
		// optional default is GET
		con.setRequestMethod("GET");
 
		//add request header
		con.setRequestProperty("User-Agent", USER_AGENT);
		con.setRequestProperty("Accept-encode", "UTF-8");
 
		int responseCode;
		try{
			responseCode = con.getResponseCode();
		}catch(IOException e){
			System.out.println(e.getMessage());
			responseCode = NO_INTERNET_REACHABILITY;
		}
		
		System.out.println("\nSending 'GET' request to URL : " + url.toString());
		System.out.println("Response Code : " + responseCode);
		
		if(responseCode == NO_INTERNET_REACHABILITY){
			return new String[]{String.valueOf(NO_INTERNET_REACHABILITY), ""};			
		}else{
			String content = retrieveBodyResponse(con, responseCode);
			if(content == null){
				content = "";
			}
			return new String[]{String.valueOf(responseCode), content};
		}
	}
	
	/*private static String getHTMLStreamForTesting(String testUrl) throws Exception{
		URL oracle = new URL(testUrl);
        URLConnection yc = oracle.openConnection();
        BufferedReader in = new BufferedReader(new InputStreamReader(
                                    yc.getInputStream()));
        
        String result = "";
        String inputLine;
        while ((inputLine = in.readLine()) != null){ 
            //System.out.println(inputLine);
            result += inputLine;
        }
        in.close();
        return new String(result.getBytes(), "UTF-8");//result;
	}*/
	
	public static Map<String, String> sendRequest(URL url, String parameters, Map<String, String> httpAuth, String method, boolean getBodyResponse, boolean watchCookies, boolean sendCookies){
		String cookieChain = "";
		if(sendCookies){
			if(cookiesList != null){
				for (int i = 0; i < cookiesList.size(); i++) {
					String[] cookieParts = cookiesList.get(i).split(";");
					cookieChain += cookieParts[0] + ";";
				}
			}
		}
		HttpURLConnection.setFollowRedirects(false);
		
		HttpURLConnection con;
		try {
			con = (HttpURLConnection)url.openConnection();
		} catch (IOException e) {
			return null;
		}
		con.setReadTimeout(TIMEOUT_MILLI);
		con.setRequestProperty("User-Agent", USER_AGENT);
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
			if(watchCookies){
				if(cookiesList == null){
					cookiesList = new ArrayList<String>();
				}else{
					cookiesList.clear();
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
	
	public static String[] sendPostRequest(URL url, String parameters, String requireId) throws Exception {
		 		
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
 
		con.setConnectTimeout(TIMEOUT_MILLI);
		//add reuqest header
		con.setRequestMethod("POST");
		con.setRequestProperty("User-Agent", USER_AGENT);
		con.setRequestProperty("Accept-Language", "es-ES,es;q=0.8");
		con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        con.setRequestProperty("Content-Length", ""+ parameters.length());
        System.out.println(parameters.length());
		if(requireId != null){
			con.setRequestProperty("X-Transmission-Session-Id", requireId);

	        String authString = TransmissionManager.user + ":" + TransmissionManager.password;
	        String authStringEnc = Base64.encodeBytes(authString.getBytes());
	        
	        con.setRequestProperty("Authorization", "Basic " + authStringEnc);
		}
 
		// Send post request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(parameters);
		wr.flush();
		wr.close();

		int responseCode;
		try{
			responseCode = con.getResponseCode();
		}catch(IOException e){
			System.out.println(e.getMessage());
			responseCode = NO_INTERNET_REACHABILITY;
		}
		System.out.println("\nSending 'POST' request to URL : " + url.toString());
		System.out.println("Post parameters : " + parameters);
		System.out.println("Response Code : " + responseCode);
		
		if(responseCode == NO_INTERNET_REACHABILITY){
			return new String[]{String.valueOf(NO_INTERNET_REACHABILITY), ""};			
		}else{
			String content = retrieveBodyResponse(con, responseCode);
			if(content == null){
				content = "";
			}
			return new String[]{String.valueOf(responseCode), content};
		}

	}
	
	private static String retrieveBodyResponse(HttpURLConnection con, int responseCode){
		BufferedReader in;
		try {
			if(responseCode != HttpURLConnection.HTTP_OK){
				in = new BufferedReader(new InputStreamReader(con.getErrorStream()));
			}else{
				in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
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
			// TODO Auto-generated catch block
			return null;
		}
		try {
			return new String(response.toString().getBytes(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			return null;
		}		
	}

}
