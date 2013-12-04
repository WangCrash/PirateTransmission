import Managers.FilmAffinityBot;
import Model.FichaPelicula;


public class pruebas {
	public static void main(String[] args) throws Exception{
		boolean initiated = FilmAffinityBot.initializeManager(false);
		
		if(initiated){
			//FichaPelicula[] result = FilmAffinityBot.getListRecommendations();
			//FichaPelicula[] result = FilmAffinityBot.getListRecommendations(FilmAffinityBot.FAMS_GENRE_KEY_ACTION);
			FichaPelicula[] result = FilmAffinityBot.searchFilm("x-men");
			if(result == null){
				System.out.println("no hay resultados");
				return;
			}else{
				System.out.println("Resultados: " + result.length);
			}
			for (int i = 0; i < result.length; i++) {
				System.out.println("------- " + i + " --------");
				System.out.println(result[i]);
				if(result[i].getTitulo().contains("Primera generaci")){
					result[i] = FilmAffinityBot.fillFichaPelicula(result[i]);
					System.out.println(result[i]);
					/*if(FilmAffinityBot.voteForFilm(result[i], "7")){
						System.out.println("Voted!");
						System.out.println(result[i]);
					}*/
					break;
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
