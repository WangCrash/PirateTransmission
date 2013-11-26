import Managers.FilmAffinityBot;
import Model.FichaPelicula;


public class pruebas {
	public static void main(String[] args) throws Exception{
		/*boolean initiated = FilmAffinityBot.initializeManager();
		
		if(initiated){
			boolean terminated = FilmAffinityBot.terminateManager();
			if(terminated)
				System.out.println("\nPROCESS CORRECTLY COMPLETED");
		}*/
		FilmAffinityBot.initializeManager(true);
		FichaPelicula[]resultado = FilmAffinityBot.searchFilm("batman");
		if(resultado != null){
			System.out.println("Encontrados " + resultado.length + " resultados");
			for (int i = 0; i < resultado.length; i++) {
				System.out.println(resultado[i]);
			}
		}
	}
}
