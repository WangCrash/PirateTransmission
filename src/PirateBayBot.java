import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Connection.SimpleConnectionManager;;



public class PirateBayBot {
	public static final int ORDERBY_DATE = 99;
	public static final int ORDERBY_SEEDERS = 7;
	public static final int CATEGORY_ALL = 0;
	public static final int CATEGORY_VIDEO = 200;
	public static final int CATEGORY_MUSIC = 101;
	//public static final int CATEGORY_APPLICATIONS = 300;
	
	private static String urlBase = "thepiratebay.sx";
	
	public static ArchivoTorrent[] searchTorrent(String search, int category, int orderBy) throws Exception{
		
		if(search.isEmpty()){
			return new ArchivoTorrent[0];
		}
		
        String query = "/search/" + URLEncoder.encode(search, "UTF-8");;
        
		query += "/0/" + orderBy + "/" + category;
        
        URI uri = new URI("http", urlBase,  query, null);
        URL url = uri.toURL();

        //String[] response = ConnectionManager.sendGetRequest(url);
        //Map<String, String> response = ConnectionManager.sendRequest(url, null, null, ConnectionManager.METHOD_GET, true, false, false);
        Map<String, String> response = SimpleConnectionManager.sendGetRequest(url, null, null);
        String responseCode = response.get("ResponseCode");
		String responseText = response.get("ResponseBody");
        //System.out.println(response);
        if(responseCode.equals("200")){
        	return listResults(responseText);
        }else{
        	System.out.println("ERROR: it seems there is a network problem");
        	return null;
        }
	}
        
    public static void getTorrent(){
            
    }
    
    private static ArchivoTorrent[] listResults(String httpResponse) throws Exception{
    	String tableRowsRegex = "<table id=\"searchResult\".*?\\<thead.*?</thead>(.*?)</table>";
        Pattern p = Pattern.compile(tableRowsRegex);
        Matcher m = p.matcher(httpResponse);
        ArrayList<ArchivoTorrent> lista = new ArrayList<ArchivoTorrent>();
        String resultados;
        int count = 0;
        if(m.find()) {
            resultados = m.group(1);
            //System.out.println("BASE: " + m.group(1));
            String rowRegex = "<tr>(.*?)</tr>";
            p = Pattern.compile(rowRegex);
            m = p.matcher(resultados);
            
            while(m.find()){
            	count++;
            	ArchivoTorrent at = new ArchivoTorrent();
                //System.out.println("------------ " + count + " ------------");
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
                    }else if(title.contains("Details") || (title.contains("Detalles"))){
                    	at.setDetailsURL(new URI("http", urlBase, link, null).toURL());
						at.setTitulo(text);
                    }else if(title.contains("magnet")){
                    	
                    	at.setMagneticLink(link);
                    }else if(title.contains("torrent")){
                    	String torrent = "http:" + link;
                    	at.setTorrentUrl(torrent);
                    }
                    //System.out.println("------fin-------");
                }
                lista.add(at);
            }
        }
        System.out.println("Resultados: " + count);
        return Arrays.copyOf(lista.toArray(), lista.toArray().length, ArchivoTorrent[].class);
    }
}
