import java.util.List;
import java.beans.Encoder;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.CookieStore;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;


public class ConnectionManager {
	private static String USER_AGENT = "orphean_navigator_2.0";
	private static final int NO_INTERNET_REACHABILITY = 0;
	private static final int TIMEOUT_MILLI = 10000;
	
	public static String[] responseByGetRequest(URL obj, boolean testing) throws Exception{
		if(testing){
			return new String[]{String.valueOf(200), getHTMLStreamForTesting("http://localhost:8081")};
		}
		
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		
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
		
		System.out.println("\nSending 'GET' request to URL : " + obj.toString());
		System.out.println("Response Code : " + responseCode);

		Map<String, List<String>> map = con.getHeaderFields();
		for (Map.Entry<String, List<String>> entry : map.entrySet()) {
			System.out.println("Key : " + entry.getKey() + 
	                 " ,Value : " + entry.getValue());
		}
		
		BufferedReader in;
		
		if(responseCode == NO_INTERNET_REACHABILITY){
			return new String[]{String.valueOf(NO_INTERNET_REACHABILITY), ""};			
		}else if(responseCode > 200){
			in = new BufferedReader(
			        new InputStreamReader(con.getErrorStream()));
			
		}else{
			in = new BufferedReader(
			        new InputStreamReader(con.getInputStream()));
		}
		String inputLine;
		StringBuffer response = new StringBuffer();
 
		while ((inputLine = in.readLine()) != null) {
			//response.append(inputLine + "\n");
			response.append(inputLine);
		}
		in.close();
		
		//print result
		return new String[]{String.valueOf(responseCode), new String(response.toString().getBytes(), "UTF-8"), map.get("Set-Cookie").toString()};
	}
	
	private static String getHTMLStreamForTesting(String testUrl) throws Exception{
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
	}
	
	public static String[] sendByPostRequest(URL obj, String urlParameters, String requireId) throws Exception {
		 		
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
 
		con.setConnectTimeout(TIMEOUT_MILLI);
		//add reuqest header
		con.setRequestMethod("POST");
		con.setRequestProperty("User-Agent", USER_AGENT);
		con.setRequestProperty("Accept-Language", "es-ES,es;q=0.8");
		con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        con.setRequestProperty("Content-Length", ""+ urlParameters.length());
        System.out.println(urlParameters.length());
		if(requireId != null){
			con.setRequestProperty("X-Transmission-Session-Id", requireId);

	        String authString = TransmissionManager.user + ":" + TransmissionManager.password;
	        String authStringEnc = Base64.encodeBytes(authString.getBytes());
	        
	        con.setRequestProperty("Authorization", "Basic " + authStringEnc);
		}
 
		// Send post request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(urlParameters);
		wr.flush();
		wr.close();

		int responseCode;
		try{
			responseCode = con.getResponseCode();
		}catch(IOException e){
			System.out.println(e.getMessage());
			responseCode = NO_INTERNET_REACHABILITY;
		}
		System.out.println("\nSending 'POST' request to URL : " + obj.toString());
		System.out.println("Post parameters : " + urlParameters);
		System.out.println("Response Code : " + responseCode);
 
		BufferedReader in;
		
		if(responseCode == NO_INTERNET_REACHABILITY){
			return new String[]{String.valueOf(NO_INTERNET_REACHABILITY), ""};			
		}else if(responseCode > 200){
			in = new BufferedReader(
			        new InputStreamReader(con.getErrorStream()));
			
		}else{
			in = new BufferedReader(
			        new InputStreamReader(con.getInputStream()));
		}
		String inputLine;
		StringBuffer response = new StringBuffer();
 
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine + "\n");
		}
		in.close();
 
		//print result
		return new String[]{String.valueOf(responseCode), response.toString()};

	}
	
	public static String[] getAuthorization(URL obj) throws Exception{
		
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		
		con.setConnectTimeout(TIMEOUT_MILLI);
 
		// optional default is GET
		con.setRequestMethod("GET");
 
		//add request header
		con.setRequestProperty("User-Agent", USER_AGENT);
		String authString = TransmissionManager.user + ":" + TransmissionManager.password;
        String authStringEnc = Base64.encodeBytes(authString.getBytes());
        
        con.setRequestProperty("Authorization", "Basic " + authStringEnc);
 
		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : " + obj.toString());
		System.out.println("Response Code : " + responseCode);
		
		BufferedReader in;		
		
		if(responseCode == NO_INTERNET_REACHABILITY){
			return new String[]{String.valueOf(NO_INTERNET_REACHABILITY), ""};			
		}else if(responseCode > 200){
			in = new BufferedReader(
			        new InputStreamReader(con.getErrorStream()));
			
		}else{
			in = new BufferedReader(
			        new InputStreamReader(con.getInputStream()));
		}
		
		String inputLine;
		StringBuffer response = new StringBuffer();
 
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine + "\n");
		}
		in.close();
 
		//print result
		return new String[]{String.valueOf(responseCode), new String(response.toString().getBytes(), "UTF-8")};
	}
	
	private static String[]  postFilmAffinityLogin(URL url, Map<String, String> parameters, String[] cookies) throws IOException{
		//String encodedUrlParameters = URLEncoder.encode(urlParameters, "UTF-8");
		String cookieChain = "";
		if(cookies != null){
			for (int i = cookies.length - 1;i >= 0; i--) {
				cookieChain += cookies[i];
				if(i != 0){
					cookieChain += "; ";
				}
			}
		}
		
		HttpURLConnection con;
		try {
			con = (HttpURLConnection)url.openConnection();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
		
		con.setReadTimeout(10000);
		try {
			con.setRequestMethod("POST");
		} catch (ProtocolException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return null;
		}
		
		//con.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
		//con.setRequestProperty("Accept-Encoding", "gzip,deflate,sdch");
		//con.setRequestProperty("Accept-Language", "es-ES,es;q=0.8,en;q=0.6");
		//con.setRequestProperty("Cache-Control", "no-cache");
		//con.setRequestProperty("Connection", "keep-alive");
		//con.setRequestProperty("Content-Length", String.valueOf(urlParameters.length()));
		//con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		if(!cookieChain.isEmpty()){
			con.setRequestProperty("Cookie", cookieChain);
		}
		//con.setRequestProperty("Host", "www.filmaffinity.com");
		//con.addRequestProperty("Origin", "filmaffinity.com");
		//con.setRequestProperty("Referer", "http://www.filmaffinity.com/es/login.php");
		con.setRequestProperty("User-Agent", USER_AGENT);
		
		System.out.println("POST REQUEST COOKIES: " + cookieChain);
		
		con.setUseCaches (false);
	    con.setDoInput(true);
	    con.setDoOutput(true);
	    
	    Set<String> keys = parameters.keySet();
		Iterator<String> keyIter = keys.iterator();
		String content = "";
		for(int i=0; keyIter.hasNext(); i++) {
			String key = keyIter.next();
			if(i!=0) {
				content += "&";
			}
			content += key + "=" + URLEncoder.encode(parameters.get(key), "UTF-8");
		}
		System.out.println(content);
	    
		try {
			con.getOutputStream().write(content.getBytes());
			con.getOutputStream().flush();
			con.getOutputStream().close();
			//con.getOutputStream().close();
		} catch (IOException e) {
			// TODO Auto-generated catch blOck
			e.printStackTrace();
			return null;
		}
		
		try {
			con.connect();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

		int responseCode;
		
		try {
			responseCode = con.getResponseCode();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		BufferedReader in;
		
		System.out.println("RESPONSE HEADERS TO POST REQUEST");
		if(responseCode == 200){
			System.out.println("I WANNA KILL AND DESTROY FILMAFFINITYYYYYYYY!!!!!!!!!");
			in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		}else if(responseCode == 302){
			System.out.println("Redireccion!!!!!!");
			in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		}else{
			System.out.println("ERROR");			
			in = new BufferedReader(new InputStreamReader(con.getErrorStream()));
		}
		for (Map.Entry<String, List<String>> responseHeader : con.getHeaderFields().entrySet()){
			if(responseHeader.getKey() == null){
				continue;
			}
			List<String> value = responseHeader.getValue();
			System.out.print("\n" + responseHeader.getKey() + ": ");
			for (String valueElement : value) {
				System.out.print(valueElement);
			}
		}
		
		String inputLine;
		StringBuffer response = new StringBuffer();
 
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine + "\n");
		}
		in.close();
		con.disconnect();
		System.out.println("\nResponse:\n" + response.toString());
		//print result
		return null;//new String[]{String.valueOf(responseCode), response.toString()};

	}
	
	private static String[] getFilmAffinityLoginPage(URL urlLogin){
		HttpURLConnection con;
		try {
			con = (HttpURLConnection)urlLogin.openConnection();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			System.out.println("Opening Connection");
			e1.printStackTrace();
			return null;
		}
		
		try {
			con.setRequestMethod("GET");
		} catch (ProtocolException e) {
			// TODO Auto-generated catch block
			System.out.println("Getting response code");
			e.printStackTrace();
			return null;
		}
		con.setReadTimeout(10000);
		
		con.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
		con.setRequestProperty("Accept-Encoding", "gzip,deflate,sdch");
		con.setRequestProperty("Accept-Language", "es-ES,es;q=0.8,en;q=0.6");
		con.setRequestProperty("Cache-Control", "max-age=0");
		con.setRequestProperty("Connection", "keep-alive");
		con.setRequestProperty("Host", "www.filmaffinity.com");
		con.setRequestProperty("Referer", "http://www.filmaffinity.com/es/login.php");
		con.setRequestProperty("User-Agent", USER_AGENT);
		
		
		try {
			con.connect();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Trying to connect");
			e.printStackTrace();
			return null;
		}
		
		int responseCode;
		try {
			responseCode = con.getResponseCode();
		} catch (IOException e) {
			responseCode = 0;
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		con.disconnect();
		if(responseCode == 200){
			ArrayList<String> cookies = new ArrayList<String>();
			for (Map.Entry<String, List<String>> responseHeader : con.getHeaderFields().entrySet()){
				if(responseHeader.getKey() == null){
					continue;
				}
				List<String> value = responseHeader.getValue();
				System.out.print("\n" + responseHeader.getKey() + ": ");
				for (String valueElement : value) {
					System.out.print(valueElement);
					if(responseHeader.getKey().equals("Set-Cookie")){
						cookies.add(valueElement);
					}
				}
			}
			return Arrays.copyOf(cookies.toArray(), cookies.toArray().length, String[].class);
		}
		
		return null;
	}
	
	public static String[]FilmAffinityLoginProcess(URL url, Map<String, String> parameters) throws Exception{
		String[] getCookies = null;//getFilmAffinityLoginPage(url);
		/*System.out.println("\nCookies:");
		for (int i = 0; i < getCookies.length; i++) {
			if(getCookies[i].contains(";")){
				getCookies[i] = getCookies[i].split(";")[0];
			}
			System.out.println("- " + getCookies[i]);
		}*/
		url = new URL("http://httpbin.org/post");
		return postFilmAffinityLogin(url, parameters, getCookies);

	}

}
