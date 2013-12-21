package GUI;

import java.util.Map;

import Managers.FilmAffinityBot;
import Managers.Manager;
import Managers.TorrentClient.TorrentClient;
import Managers.TorrentClient.TransmissionManager;
import Managers.TorrentClient.microTorrentManager;
import Utils.UtilTools;

public class ApplicationConfiguration extends Manager{
	
	public static String DEFAULT_TORRENT_CLIENT_CONFIG_KEY = "general-default-torrent-client";
	
	private static ApplicationConfiguration instance = null;
	
	private TorrentClient defaultTorrentClient;
	private String filmAffinityUser;
	private String lastFmUser;
	
	private ApplicationConfiguration(){
		initManager();
	}
	
	@Override
	public boolean initManager() {
		setDefaultTorrentClient(null);
		setFilmAffinityUser(null);
		setLastFmUser(null);
		setUpManager();
		return true;
	}

	@Override
	protected void setUpManager() {
		Map<String, String> config = new UtilTools().getConfiguration();
		
		String nameTorrentClient = config.get(DEFAULT_TORRENT_CLIENT_CONFIG_KEY);
		if(nameTorrentClient != null){
			if(nameTorrentClient.equals(microTorrentManager.MICROTORRENT_NAME_CONFIG_VALUE)){
				defaultTorrentClient = microTorrentManager.getInstance();
			}else{
				defaultTorrentClient = TransmissionManager.getInstance();
			}
		}else{
			defaultTorrentClient = TransmissionManager.getInstance();
		}
		
		filmAffinityUser = config.get(FilmAffinityBot.FILMAFFINITY_USER_AUTH_CONFIG_KEY);
		lastFmUser = config.get("Por determinar");
	}
	
	public static ApplicationConfiguration getInstance(){
		synchronized (ApplicationConfiguration.class) {
			if(instance == null){
				instance = new ApplicationConfiguration();
			}
		}
		return instance;
	}

	public TorrentClient getDefaultTorrentClient() {
		return defaultTorrentClient;
	}

	public void setDefaultTorrentClient(TorrentClient defaultTorrentClient) {
		this.defaultTorrentClient = defaultTorrentClient;
	}

	public String getFilmAffinityUser() {
		return filmAffinityUser;
	}

	public void setFilmAffinityUser(String filmAffinityUser) {
		this.filmAffinityUser = filmAffinityUser;
	}

	public String getLastFmUser() {
		return lastFmUser;
	}

	public void setLastFmUser(String lastFmUser) {
		this.lastFmUser = lastFmUser;
	}
}
