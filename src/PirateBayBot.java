import java.net.URI;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class PirateBayBot {
	
	public static void searchTorrent(String search) throws Exception{
		String urlBase = "thepiratebay.sx";
		String query = "/search/" + search + "/0/99/0";
		
		URI uri = new URI("http", urlBase, query, null);
		URL url = uri.toURL();
		
		String response = ConnectionManager.responseByGetRequest(url, true);
		
		listResults(response);
	}
	
	public static void getTorrent(){
		
	}
	
	private static String[] listResults(String httpResponse){
		String tableRowsRegex = ".*?<table id=\"searchResult\"><thead.*?</thead>(.*?)</table>.*?";
		Pattern p = Pattern.compile(tableRowsRegex);
		Matcher m = p.matcher(httpResponse);
	    String resultados;
	    if(m.find()) {
            System.out.println("Num de grupos: " + m.group(1));
            resultados = m.group(1);
            String rowRegex = "<tr>(.*?)</tr>";
            p = Pattern.compile(rowRegex);
            m = p.matcher(resultados);
            int count = 0;
            while(m.find()){
            	count++;
            	System.out.println("------------ " + count + " ------------");
            	String fieldRegex = "<td.*?>(.*?)</td>";
            	Pattern subP = Pattern.compile(fieldRegex);
            	System.out.println(m.group());
            	Matcher subM = subP.matcher(m.group());
            	while (subM.find()) {
					System.out.println(subM.group());
				}
            }
	    }
		return new String[5];
	}
}
