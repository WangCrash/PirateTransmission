import Managers.FilmAffinityBot;


public class pruebas {
	public static void main(String[] args) throws Exception{
		/*boolean initiated = FilmAffinityBot.initializeManager();
		
		if(initiated){
			boolean terminated = FilmAffinityBot.terminateManager();
			if(terminated)
				System.out.println("\nPROCESS CORRECTLY COMPLETED");
		}*/
		FilmAffinityBot.initializeManager(true);
		FilmAffinityBot.searchFilm("amor a quemarropa");
	}
}
