import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;


public class FilmAffinityBot {
	private static String urlBase = "filmaffinity.com";
	
	public static void searchFilm(String search) throws Exception{
		if(search.isEmpty()){
			return;
		}
		
        String query = "/es/search.php?stext=" + URLEncoder.encode(search, "UTF-8") + "&stype=title";
        
        URI uri = new URI("http", urlBase,  query, null);
        URL url = uri.toURL();

        String[] response = ConnectionManager.responseByGetRequest(url, false);
        System.out.println(response[1]);
	}
}
