import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;


public class ConnectionManager {
	private static String USER_AGENT = "chrome";//"orphean_navigator_2.0";
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
		return new String[]{String.valueOf(responseCode), new String(response.toString().getBytes(), "UTF-8")};
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
		con.setRequestProperty("Accept-Language", "es-ES,es;q=0.5");
		con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        con.setRequestProperty("Content-Length", ""+ urlParameters.length());
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

}
