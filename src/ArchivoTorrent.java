import java.net.URL;


public class ArchivoTorrent {
	private String titulo;
	private String torrentUrl;
	private String magneticLink;
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
	public String getMagneticLink() {
		return magneticLink;
	}
	public void setMagneticLink(String magneticLink) {
		this.magneticLink = magneticLink;
	}
	public String getTorrentUrl() {
		return torrentUrl;
	}
	public void setTorrentUrl(String torrentUrl) {
		this.torrentUrl = torrentUrl;
	}
	
	@Override
	public String toString(){
		String respuesta = ""; 
		respuesta = "Titulo: " + this.getTitulo() + "\n";
		respuesta += "Categoría: " + this.getCategoria() + "\n";
		if(this.detailsURL != null)
			respuesta += "Details URL: " + this.getDetailsURL().toString() + "\n";
		if(this.getTorrentUrl() != null)
			respuesta += "Torrent URL: " + this.getTorrentUrl().toString() + "\n";
		if(this.getMagneticLink() != null)
			respuesta += "Magnet Link: " + this.getMagneticLink() + "\n"; 
		return respuesta;
	}
}
