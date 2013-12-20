package Managers.TorrentClient;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Managers.Manager;
import Model.ArchivoTorrent;

public abstract class TorrentClient extends Manager{
	public static String TORRENT_CLIENT_SERVER_KEY = "";
	public static String TORRENT_CLIENT_USER_KEY = "";
	public static String TORRENT_CLIENT_PASSWORD = "";
	
	public abstract boolean addTorrent(ArchivoTorrent torrent);
	
	public abstract String getServerConfigKey();
	public abstract String getUserConfigKey();
	public abstract String getPasswordConfigKey();
	
	//public abstract ArchivoTorrent[] listTorrents();
	
	protected String getTokenFromHTML(String regEx, String html){
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(html);
		//<code>X-Transmission-Session-Id: jSlV7sWsU81qptl1OU8UnniTM6HfIWIL1YOektP7CxeLeFwF</code>
		String token = null;
		if(m.find()){
			 token = m.group(1).trim();
		}
		return token;
	}
}
