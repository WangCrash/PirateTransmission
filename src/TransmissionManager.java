import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import JSON.*;

public class TransmissionManager {

	private static String urlBase = "//iriene.dlinkddns.com:9091";
	private static int requestTag = 0;
	private static String transmissionId = "5L38jNKUmN8ZiYP4Htx66kK0yXSn0QubVV2DuKVfuZly6tXP";
	
	public static void addTorrent(ArchivoTorrent torrent) throws URISyntaxException, MalformedURLException{
		String torrentUrl = (torrent.getTorrentUrl() != null)?torrent.getTorrentUrl():torrent.getMagneticLink();
		JSONArray jsonTorrentUrls = new JSONArray();
		jsonTorrentUrls.put(torrentUrl);
		JSONObject jsonArguments = new JSONObject().put("trackerAdd", jsonTorrentUrls);
		JSONObject jsonRequest = new JSONObject();
		jsonRequest.put("method", "torrent-set");
		jsonRequest.put("arguments", jsonArguments);
		jsonRequest.put("tag", requestTag);
		URI uri = new URI("http", urlBase, null);
        URL url = uri.toURL();
        System.out.println(url.toString());
		try {
			String response = ConnectionManager.responseByPostRequest(url, jsonRequest.toString(), transmissionId);
			requestTag++;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
	}
}
