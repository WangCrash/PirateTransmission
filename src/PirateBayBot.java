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
                //String fieldRegex = "<td.*?>(.*?)</td>";
                String fieldRegex = "\\<td\\sclass=\"vertTh\"\\>\\<a.*?\\>(.*?)\\</a\\>";//.*?<div.*?class=\"detName\".*?<a href=\"(.*?)\".*?class=\"detLink\".*?>(.*?)</a>.*?</td>";
                Pattern subP = Pattern.compile(fieldRegex);
                //System.out.println(m.group());
                Matcher subM = subP.matcher(m.group());
                int count2 = 0;
                while (subM.find()) {
                    count2++;
                    System.out.println(subM.groupCount() + " grupos");
                    for (int i = 1; i < subM.groupCount(); i++) {
                        if(i == 1){
                        	System.out.print("Categoría: ");
                        }else{
                            System.out.print("Titulo: ");
                        }
                        System.out.println(subM.group(i));
                    }
                }
                if(count2 == 0)
        			System.out.println("NOTHING FOUND");
            }
        }
        return new String[5];
    }
}