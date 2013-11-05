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
	@Override
	public String toString(){
		String respuesta = ""; 
		respuesta = "Titulo: " + this.getTitulo() + "\n";
		respuesta += "Categoría: " + this.getCategoria() + "\n";
		respuesta += "Details URL: " + this.getDetailsURL().toString() + "\n";
		respuesta += "Torrent URL: " + this.getTorrentUrl().toString() + "\n";
		return respuesta;
	}
}
