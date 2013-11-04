import java.net.URL;


public class ArchivoTorrent {
	private String titulo;
	private URL torrentUrl;
	private URL detailsURL;
	private String categoria;
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	public URL getDetailsURL() {
		return detailsURL;
	}
	public void setDetailsURL(URL detailsURL) {
		this.detailsURL = detailsURL;
	}
	public URL getTorrentUrl() {
		return torrentUrl;
	}
	public void setTorrentUrl(URL torrentUrl) {
		this.torrentUrl = torrentUrl;
	}	
}
