package GUI.Pre;

import Managers.ApplicationConfiguration;
import Managers.PirateBayBot;
import Managers.Helpers.FilmAffinityBot;
import Managers.Helpers.LastFMManager;
import Managers.Persistent.PersistentDataManager;

public class PreApp {
	private PreAppView loadingView;
	
	public PreApp(PreAppView loadingView){
		this.loadingView = loadingView;
	}
	
	public void initializingApplication() {
		System.out.println("INCIANDO APLICACION...");
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			System.out.println(e.getMessage());
		}
		loadingView.getMessageLabel().setText("Leyendo fichero de configuraci�n...");
		ApplicationConfiguration.getInstance().initManager();
		
		loadingView.getMessageLabel().setText("Cargando configuraci�n de cliente Torrent...");
		if(!ApplicationConfiguration.getInstance().getDefaultTorrentClient().initManager()){
			String clientName = ApplicationConfiguration.getInstance().getDefaultTorrentClient().getTorrentClientName();
			loadingView.getMessageLabel().setText("Imposible conectar con " + clientName + "...");
		}
		
		loadingView.getMessageLabel().setText("Conectando con PirateBay...");
		if(!PirateBayBot.getInstance().initManager()){
			loadingView.getMessageLabel().setText("Imposible conectar con PirateBay...");
		}
		
		loadingView.getMessageLabel().setText("Iniciando sesi�n en Last FM...");
		if(LastFMManager.getInstance().initManager()){
			loadingView.getMessageLabel().setText("Recuperando colecci�n...");
		}
		
		loadingView.getMessageLabel().setText("Iniciando sesi�n en FilmAffinity...");
		if(!FilmAffinityBot.getInstance().initManager()){
			loadingView.getMessageLabel().setText("Imposible iniciar sessi�n en FilmAffinity");
		}
		
		loadingView.getMessageLabel().setText("Iniciando controladores de persistencia...");
		if(!PersistentDataManager.getInstance().initManager()){
			System.err.println("PERSISTENDDATAMANAGER not initialized");
		}
	}
}
