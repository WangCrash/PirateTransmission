import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Testing {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		if(TransmissionManager.initManager()){
			runShell();
		}else{
			System.out.println("External error: couldn't authenticate on Transmission.");
		}
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
		System.out.print("Buscar torrent: ");
		return leerTeclado();
	}
	
	private static void runShell() throws Exception{
		do{
			showTitle();
			System.out.println("");
			System.out.println("");
			System.out.println("");
			System.out.println("");
			runApp();
			System.out.print("Nueva búsqueda?(s/n)");
			String respuesta = leerTeclado();
			if(respuesta.equalsIgnoreCase("n")){
				break;
			}
		}while(true);
	}
	
	private static void runApp() throws Exception{
		String busqueda = showBusqueda();
		ArchivoTorrent[] resultados = PirateBayBot.searchTorrent(busqueda);
		
		if(resultados.length == 0){
			return;
		}
		
        for (int i = 0; i < resultados.length; i++) {
			System.out.println((i+1) + ".-");
			System.out.println("   Titulo: " + resultados[i].getTitulo());
			System.out.println("   Detalles(web):" + resultados[i].getDetailsURL().toString());
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
                
        System.out.println(TransmissionManager.addTorrent(resultados[opcion-1])?"Torrent añadido":"El torrent no se ha podido añadir");
		
	}
	
	private static String leerTeclado(){
		Scanner sc = new Scanner(System.in);
		return sc.nextLine();
	}
}
