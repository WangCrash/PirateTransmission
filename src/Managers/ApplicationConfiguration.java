package Managers;

import java.util.Map;

import Managers.TorrentClient.TorrentClient;
import Managers.TorrentClient.TransmissionManager;
import Managers.TorrentClient.microTorrentManager;
import Utils.UtilTools;

public class ApplicationConfiguration extends Manager{
	
	public static String DEFAULT_TORRENT_CLIENT_CONFIG_KEY = "general-default-torrent-client";
	
	private static ApplicationConfiguration instance = null;
	
	private TorrentClient defaultTorrentClient;
	
	private ApplicationConfiguration(){
		initManager();
	}
	
	@Override
	public boolean initManager() {
		setDefaultTorrentClient(null);
		setUpManager();
		return true;
	}

	@Override
	protected void setUpManager() {
		Map<String, String> config = new UtilTools().getConfiguration();
		if(config == null){
			return;
		}		
		
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
		System.out.println("Cliente torrent principal: " + nameTorrentClient);
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

	@Override
	public boolean isStared() {
		return (instance != null);
	}

}
