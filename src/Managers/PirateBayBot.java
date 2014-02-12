package Managers;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Connection.SimpleConnectionManager;
import Model.ArchivoTorrent;
import Utils.UtilTools;



public class PirateBayBot extends Manager{
	private static PirateBayBot instance = null;
	
	public static final int ORDERBY_DATE = 99;
	public static final int ORDERBY_SEEDERS = 7;
	public static final int CATEGORY_ALL = 0;
	public static final int CATEGORY_VIDEO = 200;
	public static final int CATEGORY_MUSIC = 101;
	//public static final int CATEGORY_APPLICATIONS = 300;
	
	public static final String PIRATEBAYBOT_URL_BASE_CONFIG_KEY = "piratebay-url";
	
	public String urlBase;
	
	private boolean initialized;
	
	private PirateBayBot(){
		initialized = false;
		urlBase = "//thepiratebay.org";
	}
	
	public static PirateBayBot getInstance(){
		synchronized (PirateBayBot.class) {
			if(instance == null){
				instance = new PirateBayBot();
			}
		}
		return instance;
	}
	
	@Override
	public boolean initManager() {
		setUpManager();
		URL url;
		try {
			url =  new URL("http:" + urlBase);
			System.out.println("PIRATE BAY URL: " + url);
		} catch (MalformedURLException e) {
			initialized = false;
			return initialized;
		}
		Map<String, String> response = new SimpleConnectionManager().sendGetRequest(url, null, null);
		String responseCode = response.get("ResponseCode");
		if(responseCode.equals("200")){
			System.out.println("Response code: 200");
			initialized = true;
		}else if(responseCode.equals("301") || (responseCode.equals("302"))){
			System.out.println("Response code: 301");
			urlBase = response.get("Location");
			if(urlBase.startsWith("http:")){
				urlBase = urlBase.replace("http:", "");
			}
			if(urlBase.endsWith("/")){
				urlBase = urlBase.substring(0, urlBase.length() - 1);
			}
			initialized = true;
		}else{
			System.out.println("Response code: " + responseCode);
		}
		System.out.println("PirateBay: " + urlBase);
		return initialized;
	}

	@Override
	public void setUpManager() {
		Map<String, String> config = new UtilTools().getConfiguration();
		if(config == null){
			return;
		}
		
		String newUrl = config.get(PIRATEBAYBOT_URL_BASE_CONFIG_KEY);
		if(newUrl != null){
			urlBase = newUrl;
		}
		initialized = false;
	}
	
	public ArchivoTorrent[] searchTorrent(String search, int category, int orderBy){
		if(!initialized){
			return null;
		}
		
		if(search.isEmpty()){
			return new ArchivoTorrent[0];
		}
        String query = "/search/" + new UtilTools().encodeString(search);

        
		query += "/0/" + orderBy + "/" + category;
		
		System.out.println(query);
        
        URI uri;
		try {
			uri = new URI("http", urlBase + query, null);
		} catch (URISyntaxException e) {
			return null;
		}
        URL url;
		try {
			url = uri.toURL();
		} catch (MalformedURLException e) {
			return null;
		}

        //String[] response = ConnectionManager.sendGetRequest(url);
        //Map<String, String> response = ConnectionManager.sendRequest(url, null, null, ConnectionManager.METHOD_GET, true, false, false);
        Map<String, String> response = new SimpleConnectionManager().sendGetRequest(url, null, null);
        String responseCode = response.get("ResponseCode");
		String responseText = response.get("ResponseBody");
        //System.out.println(response);
        if(responseCode.equals("200")){
        	return listResults(responseText);
        }else{
        	System.out.println("ERROR: something wrong happened");
        	return null;
        }
	}
	
	public boolean isInitialized(){
		return initialized;
	}
    
    private ArchivoTorrent[] listResults(String htmlResponse){
    	String simpleUrlBase = urlBase.substring(2);
    	System.out.println(htmlResponse);
    	String tableRowsRegex = "<table id=\"searchResult\".*?\\<thead.*?</thead>(.*?)</table>";
        Pattern p = Pattern.compile(tableRowsRegex);
        Matcher m = p.matcher(htmlResponse);
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
                    	try {
                    		at.setDetailsURL(new URI("http", simpleUrlBase, link, null).toURL());
						} catch (MalformedURLException e) {
							System.out.println("MalformedURLException");
							System.out.println(e.getMessage());
							System.out.println("URL de detalles no se ha podido guardar: ");
							System.out.println("Link: " + link);
						} catch (URISyntaxException e) {
							System.out.println("URISyntaxException");
							System.out.println(e.getMessage());
							System.out.println("URL de detalles no se ha podido guardar: ");
							System.out.println("Link: " + link);
						}
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
        return Arrays.copyOf(lista.toArray(), lista.size(), ArchivoTorrent[].class);
    }

	@Override
	public boolean isStarted() {
		return initialized;
	}
	
	public static void main(String[] args){
		PirateBayBot.getInstance().initManager();
	}
}
