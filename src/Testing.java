public class Testing {

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
		ArchivoTorrent[] resultados = PirateBayBot.searchTorrent("leolo");
		
        for (int i = 0; i < resultados.length; i++) {
			System.out.println((i+1) + "- " + resultados[i].getTitulo());
		}
        if(TransmissionManager.loginOnTranssmission()){
        	TransmissionManager.addTorrent(resultados[10]);
        }
	}
}
