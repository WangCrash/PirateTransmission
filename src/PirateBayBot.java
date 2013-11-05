import java.net.URI;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class PirateBayBot {
	private static String urlBase = "thepiratebay.sx";
	
	public static void searchTorrent(String search) throws Exception{
        String query = "/search/" + search + "/0/99/0";
        
        URI uri = new URI("http", urlBase, query, null);
        URL url = uri.toURL();
        
        String response = ConnectionManager.responseByGetRequest(url, true);
        
        ArchivoTorrent[] lista = listResults(response);
        System.out.println(lista.toString());
	}
        
    public static void getTorrent(){
            
    }
    
    private static ArchivoTorrent[] listResults(String httpResponse) throws Exception{
    	String tableRowsRegex = ".*?\\<table id=\"searchResult\".*?\\<thead.*?\\</thead\\>(.*?)\\</table\\>.*?";
        Pattern p = Pattern.compile(tableRowsRegex);
        Matcher m = p.matcher(httpResponse);
        ArrayList<ArchivoTorrent> lista = new ArrayList<ArchivoTorrent>();
        String resultados;
        if(m.find()) {
            resultados = m.group(1);
            System.out.println("BASE: " + m.group(1));
            String rowRegex = "\\<tr\\>(.*?)\\</tr\\>";
            p = Pattern.compile(rowRegex);
            m = p.matcher(resultados);
            int count = 0;
            while(m.find()){
            	count++;
                System.out.println("------------ " + count + " ------------");
                String fieldRegex = "\\<td.*?\\>(.*?)\\</td\\>";
                fieldRegex = "<a href=\"(.*?)\".*?title=\"(.*?)\">(.*?)</a>";//"\\<a href=\"(.*?)\".*?title=\"(.*?)\".*?\\>(.*?)\\</a\\>";
                Pattern subP = Pattern.compile(fieldRegex);
                //System.out.println(m.group());
                Matcher subM = subP.matcher(m.group());
                String link = "";
                String title = "";
                String text = "";
                int field = 0;
                ArchivoTorrent at = new ArchivoTorrent();
                while (subM.find()) {
                	//System.out.println("Num. grupos: " + subM.groupCount());
                    for (int i = 0; i <= subM.groupCount(); i++) {
						System.out.println(subM.group(i));
						switch(i){
						case 1:
							link = subM.group(i);
							break;
						case 2:
							title = subM.group(i);
							break;
						case 3:
							text = subM.group(i);
							break;
						}
					}
                    switch(field%3){
					case 0:
						at.setCategoria(text);
						break;
					case 1:
						at.setDetailsURL(new URI(link).toURL());
						at.setTitulo(text);
						break;
					case 2:
						if(link.contains("//torrents.") || link.contains("magnet:")){
							at.setTorrentUrl(new URI("http", urlBase, link, null).toURL());
						}
						break;
					}
					
					field++;
                    System.out.println("------fin-------");
                }
                if(!(at.getTorrentUrl() == null) || (at.getTorrentUrl().toString().isEmpty())){
                	lista.add(at);
                }
            }
        }
        return (ArchivoTorrent[])lista.toArray();
    }
}
