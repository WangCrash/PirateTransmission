import java.net.URI;
import java.net.URL;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class PirateBayBot {
	private static String urlBase = "thepiratebay.sx";
	
	public static ArchivoTorrent[] searchTorrent(String search) throws Exception{
        String query = "/search/" + search + "/0/99/0";
        
        URI uri = new URI("http", urlBase, query, null);
        URL url = uri.toURL();
        
        String response = ConnectionManager.responseByGetRequest(url, true);
        
        return listResults(response);
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
            	ArchivoTorrent at = new ArchivoTorrent();
                System.out.println("------------ " + count + " ------------");
                //String fieldRegex = "\\<td.*?\\>(.*?)\\</td\\>";
                String fieldRegex = "<a href=\"(.*?)\".*?title=\"(.*?)\">(.*?)</a>";
                Pattern subP = Pattern.compile(fieldRegex);
                //System.out.println(m.group());
                Matcher subM = subP.matcher(m.group());
                String link = "";
                String title = "";
                String text = "";
                while (subM.find()) {
                	//System.out.println("Num. grupos: " + subM.groupCount());
                    for (int i = 0; i <= subM.groupCount(); i++) {
						//System.out.println(subM.group(i));
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
                    if(title.contains("categor")){
                    	if((at.getCategoria() != null) && !at.getCategoria().isEmpty()){
                    		at.setCategoria(at.getCategoria() + " -> " + text);
                    	}else{
                    		at.setCategoria(text);
                    	}
                    }else if(title.contains("Detalles")){
                    	at.setDetailsURL(new URI("http", urlBase, link, null).toURL());
						at.setTitulo(text);
                    }else if(title.contains("magnet")){
                    	
                    	at.setMagneticLink(link);
                    }else if(title.contains("torrent")){
                    	at.setTorrentUrl(link);
                    }
                    //System.out.println("------fin-------");
                }
                lista.add(at);
            }
        }
        return Arrays.copyOf(lista.toArray(), lista.toArray().length, ArchivoTorrent[].class);
    }
}
