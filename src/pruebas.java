import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Managers.FilmAffinityBot;
import Model.FichaPelicula;


public class pruebas {
	public static void main(String[] args) throws Exception{
		boolean initiated = FilmAffinityBot.initializeManager(false);
		
		if(initiated){
			FichaPelicula[] result = FilmAffinityBot.searchFilm("miedo y asco en las vegas");//FilmAffinityBot.getListRecommendations();
			if(result == null)
				return;
			for (int i = 0; i < result.length; i++) {
				System.out.println(result[i]);
				if(FilmAffinityBot.voteForFilm(result[0], "6")){
					System.out.println("Voted!");
				}
			}
			boolean terminated = FilmAffinityBot.terminateManager();
			if(terminated)
				System.out.println("\nPROCESS CORRECTLY COMPLETED");
		}
		/*FilmAffinityBot.initializeManager(true);
		FichaPelicula[]resultado = FilmAffinityBot.searchFilm("batman");
		if(resultado != null){
			System.out.println("Encontrados " + resultado.length + " resultados");
			for (int i = 0; i < resultado.length; i++) {
				System.out.println(resultado[i]);
			}
		}*/
	}
}
