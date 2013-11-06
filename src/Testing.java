import java.io.*;
import java.net.*;

public class Testing {
	private static String USER_AGENT = "orphean_navigator_2.0";

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		/*URL oracle = new URL("http://thepiratebay.sx/");
        URLConnection yc = oracle.openConnection();
        BufferedReader in = new BufferedReader(new InputStreamReader(
                                    yc.getInputStream()));
        String inputLine;
        while ((inputLine = in.readLine()) != null) 
            System.out.println(inputLine);
        in.close();*/
		ArchivoTorrent[] resultados = PirateBayBot.searchTorrent("127 horas");
		
        for (int i = 0; i < resultados.length; i++) {
			System.out.println(resultados[i].toString());
		}
        TransmissionManager.loginOnTranssmission();
        TransmissionManager.addTorrent(resultados[10]);
	}
}
