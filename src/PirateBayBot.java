import java.net.URI;
import java.net.URL;
import java.util.Locale.Category;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class PirateBayBot {
	private static String urlBase = "thepiratebay.sx";
	
	public static void searchTorrent(String search) throws Exception{
        String query = "/search/" + search + "/0/99/0";
        
        URI uri = new URI("http", urlBase, query, null);
        URL url = uri.toURL();
        
        String response = ConnectionManager.responseByGetRequest(url, true);
        
        listResults(response);
	}
        
    public static void getTorrent(){
            
    }
    
    private static String[] listResults(String httpResponse){
    	String tableRowsRegex = ".*?\\<table id=\"searchResult\".*?\\<thead.*?\\</thead\\>(.*?)\\</table\\>.*?";
        Pattern p = Pattern.compile(tableRowsRegex);
        Matcher m = p.matcher(httpResponse);
        String resultados;
        if(m.find()) {
            resultados = m.group(1);
            System.out.println(m.group(1));
            String rowRegex = "\\<tr\\>(.*?)\\</tr\\>";
            p = Pattern.compile(rowRegex);
            m = p.matcher(resultados);
            int count = 0;
            while(m.find()){
            	count++;
                System.out.println("------------ " + count + " ------------");
                String fieldRegex = "\\<td.*?\\>(.*?)\\</td\\>";
                fieldRegex = "<a href.*?>.*?</a>";//"\\<a href=\"(.*?)\".*?title=\"(.*?)\".*?\\>(.*?)\\</a\\>";
                Pattern subP = Pattern.compile(fieldRegex);
                //System.out.println(m.group());
                Matcher subM = subP.matcher(m.group());
                String link = "";
                String title = "";
                String text = "";
                while (subM.find()) {
                	//System.out.println("Num. grupos: " + subM.groupCount());
                    for (int i = 0; i < subM.groupCount(); i++) {
						System.out.println(subM.group(i));
						if(i == 1){
							link = subM.group(i);
						}else if(i == 2){
							title = subM.group(i);
						}else if(i == 3){
							text = subM.group(i);
						}
					}
                }
            }
        }
        return new String[5];
    }
}