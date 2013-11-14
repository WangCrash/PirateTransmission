import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class PTConsoleApp {

	private static final String ADD_TORRENT = "Añadir Torrent";
	private static final String SHOW_DETAILS = "Ver Detalles";
	private static final String RETURN_TO_MAIN_MENU = "Volver";
	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		runShell();
		/*if(TransmissionManager.initManager()){
			TransmissionManager.listTorrents();
			runShell();
		}else{
			System.out.println("External error: couldn't authenticate on Transmission.");
		}*/
	}
	
	private static void showTitle() throws IOException{
		BufferedReader bf = new BufferedReader(new FileReader("titulo"));
		String sCadena;
		while ((sCadena = bf.readLine())!=null) {
		   System.out.println(sCadena);
		}
		bf.close();
	}
	
	private static String showBusqueda(){
		String busqueda;
		do{
			System.out.print("Buscar torrent: ");
			busqueda = leerTeclado();
		}while(busqueda.isEmpty());
		return busqueda;
	}
	
	private static ArchivoTorrent[] getSearchResults(String busqueda) throws Exception {
		
		return PirateBayBot.searchTorrent(busqueda, PirateBayBot.CATEGORY_ALL, PirateBayBot.ORDERBY_SEEDERS);
	}
	
	private static void runShell() throws Exception{
		do{
			showTitle();
			System.out.println("");
			System.out.println("");
			System.out.println("");
			System.out.println("");
			String busqueda = showBusqueda();			
			runApp(getSearchResults(busqueda));
			System.out.print("Nueva búsqueda?(s/n)");
			String respuesta = leerTeclado();
			if(respuesta.equalsIgnoreCase("n")){
				break;
			}
		}while(true);
	}
	
	private static void runApp(ArchivoTorrent[] resultados) throws Exception{
		
		
		if((resultados == null) || (resultados.length == 0)){
			return;
		}
		
        for (int i = 0; i < resultados.length; i++) {
			System.out.println((i+1) + ".-");
			System.out.println("   " + resultados[i].getCategoria());
			System.out.println("   Titulo: " + resultados[i].getTitulo());
			//System.out.println("   Detalles(web):" + resultados[i].getDetailsURL().toString());
		}
        
        int opcion;
        do {
        	System.out.print("Torrent: ");
        	String respuesta = leerTeclado();
        	try{
        		opcion = Integer.parseInt(respuesta);
        		if(opcion > 0 && opcion <= resultados.length)
        			break;
        	}catch(Exception e){
        		System.out.println("ERROR: se esperaba nœmero de torrent");
        	}
		} while (true);
             
        showTorrentMenu(resultados, opcion-1);
        //System.out.println(TransmissionManager.addTorrent(resultados[opcion-1])?"Torrent añadido":"El torrent no se ha podido añadir");
		
	}
	
	private static void showTorrentMenu(ArchivoTorrent[] torrents, int indice) throws Exception{
		int opcion;
		ArchivoTorrent torrent = torrents[indice];
		ArrayList<String> optionsList = new ArrayList<String>();
		if(torrent.getTorrentUrl() != null){
			optionsList.add(ADD_TORRENT);
		}else if(torrent.getMagneticLink() != null){
			optionsList.add(ADD_TORRENT);
		}
		if(torrent.getDetailsURL() != null){
			optionsList.add(SHOW_DETAILS);
		}
		optionsList.add(RETURN_TO_MAIN_MENU);
		
		String[] opciones = Arrays.copyOf(optionsList.toArray(), optionsList.toArray().length, String[].class);
		do{
			System.out.println("");
			System.out.println(torrent.getTitulo());
			System.out.println("");
			for (int i = 0; i < opciones.length; i++) {
				System.out.println((i+1) + ".- " + opciones[i]);
			}
			String respuesta = leerTeclado();			
			try{
        		opcion = Integer.parseInt(respuesta);
        		if(opcion < 0 && opcion > opciones.length){
        			
        			continue;
        		}
        		
        		String accion = opciones[opcion - 1];
        		if(accion.equals(ADD_TORRENT)){
        			System.out.println(TransmissionManager.addTorrent(torrent)?"Torrent añadido":"El torrent no se ha podido añadir");
        			break;
        		}else if(accion.equals(SHOW_DETAILS)){
        			System.out.println("opening web browser...");
        			openURLInNavigator(torrent.getDetailsURL());
        		}else if(accion.equals(RETURN_TO_MAIN_MENU)){
        			runApp(torrents);
        			break;
        		}
        	}catch(Exception e){
        		System.out.println("ERROR: se esperaba nœmero de torrent");
        	}
		}while(true);
	}
	
	private static void openURLInNavigator(URL url){
		if(Desktop.isDesktopSupported())
		{
		  try {
			Desktop.getDesktop().browse(url.toURI());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("couldn't open that URL");
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				System.out.println("couldn't open that URL");
			}
		}else{
			System.out.println("Web browser not found");
		}
	}
	
	private static String leerTeclado(){
		Scanner sc = new Scanner(System.in);
		return sc.nextLine();
	}
}
