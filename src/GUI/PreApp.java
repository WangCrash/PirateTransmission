package GUI;

import Managers.ApplicationConfiguration;
import Managers.PirateBayBot;
import Managers.Helpers.FilmAffinityBot;
import Managers.Helpers.LastFMManager;
import Utils.UtilTools;

public class PreApp {
	private LoadingView loadingView;
	
	public void initializingApplication() {
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				String message = "";
				ApplicationConfiguration.getInstance().initManager();
				
				if(!ApplicationConfiguration.getInstance().getDefaultTorrentClient().initManager()){
					String clientName = ApplicationConfiguration.getInstance().getDefaultTorrentClient().getTorrentClientName();
					message += "\n - No se ha podido conectar con " + clientName + ".";
				}
				if(!PirateBayBot.getInstance().initManager()){
					message += "\n - No se ha podido conectar con PirateBay.";
				}
				if(!LastFMManager.getInstance().initManager()){
					message = "\n - No se ha podido iniciar sesión en Film Affinity";
				}
				if(!FilmAffinityBot.getInstance().initManager()){
					message += "\n - No se ha podido iniciar sesión en LastFM";						
				}
				if(!message.isEmpty()){
					new UtilTools().showWarningDialog(null, "Error", "Se encontraron los siguiente errores:" + message);
				}
				hideLoadingView();
			}
		});
		t.start();
		showLoadingView();
	}
	
	private void showLoadingView() {
		loadingView = new LoadingView(null);
		loadingView.setMessageLabel("Iniciando Aplicación ...");
		loadingView.setVisible(true);
	}
	
	private void hideLoadingView(){
		loadingView.setVisible(false);
		loadingView = null;
	}
}
