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
					message += "No se ha podido conectar con " + clientName + ".";
				}
				PirateBayBot.getInstance().initManager();
				if(LastFMManager.getInstance().initManager()){
					Thread t = new Thread(new Runnable() {
						@Override
						public void run() {
							LastFMManager.getInstance().getRecommendations();
						}
					});
					t.start();
				}
				FilmAffinityBot.getInstance().initManager();
				
				if(!message.isEmpty()){
					new UtilTools().showWarningDialog(null, "Error", message);
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
