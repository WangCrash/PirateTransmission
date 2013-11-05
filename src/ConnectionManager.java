import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;


public class ConnectionManager {
	private static String USER_AGENT = "orphean_navigator_2.0";
	
	public static String responseByGetRequest(URL obj, boolean testing) throws Exception{
		if(testing){
			return getHTMLStreamForTesting("http://localhost:8080");
		}
		
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
 
		// optional default is GET
		con.setRequestMethod("GET");
 
		//add request header
		con.setRequestProperty("User-Agent", USER_AGENT);
 
		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : " + obj.toString());
		System.out.println("Response Code : " + responseCode);
 
		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
 
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine + "\n");
		}
		in.close();
 
		//print result
		return new String(response.toString().getBytes(), "UTF-8");
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
	
	public static String responseByPostRequest(URL obj, String urlParameters, String requireId) throws Exception {
		 		
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
 
		//add reuqest header
		con.setRequestMethod("POST");
		con.setRequestProperty("User-Agent", USER_AGENT);
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
		if(requireId != null){
			con.setRequestProperty("X-Transmission-Session-Id", requireId);
		}
 
		//String urlParameters = "sn=C02G8416DRJM&cn=&locale=&caller=&num=12345";
		//String urlParameters = "s='la prueba'";
 
		// Send post request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(urlParameters);
		wr.flush();
		wr.close();
 
		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'POST' request to URL : " + obj.toString());
		System.out.println("Post parameters : " + urlParameters);
		System.out.println("Response Code : " + responseCode);
 
		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
 
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine + "\n");
		}
		in.close();
 
		//print result
		return response.toString();
 
	}

}
