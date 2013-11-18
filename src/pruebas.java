
public class pruebas {
	public static void main(String[] args) throws Exception{
		boolean logged = FilmAffinityBot.login();
		if(logged){
			System.out.println("Estoy dentro, MUAHAHAHAHAHAHAHAHAHA");
		}else{
			System.out.println("Os ordeno, dejadme ENTRAAAR!!");
		}
		boolean loggedOut = FilmAffinityBot.logout();
		if(loggedOut){
			System.out.println("Logout");
		}else{
			System.out.println("Algo ha fallado");
		}
	}
}
