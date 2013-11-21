package Model;

import java.util.Map;

public class FichaPelicula {
	public static final String MAP_KEY_PREMIOS_AÑO = "año";
	public static final String MAP_KEY_PREMIOS_PREMIO = "premio";
	public static final String MAP_KEY_CRITICAS_TEXTO = "texto";
	public static final String MAP_KEY_CRITICAS_AUTOR_REVISTA = "autor_revista";
	
	private String imageUrl;
	private String filmDetailsUrl;
	private String titulo;
	private String tituloOriginal;
	private String año;
	private String duracion;
	private String pais;
	private String[] director;
	private String guion;
	private String musica;
	private String fotografia;
	private String[] reparto;
	private String productora;
	private String genero;
	private String sinopsis;
	private Map<String, String> premios;
	private Map<String, String[]> criticas;
	private String valoracion;
	private String notaUsuario;
	private String notaAlmasGemelas;
	
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public String getFilmDetailsUrl() {
		return filmDetailsUrl;
	}
	public void setFilmDetailsUrl(String filmDetailsUrl) {
		this.filmDetailsUrl = filmDetailsUrl;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getTituloOriginal() {
		return tituloOriginal;
	}
	public void setTituloOriginal(String tituloOriginal) {
		this.tituloOriginal = tituloOriginal;
	}
	public String getAño() {
		return año;
	}
	public void setAño(String año) {
		this.año = año;
	}
	public String getDuracion() {
		return duracion;
	}
	public void setDuracion(String duracion) {
		this.duracion = duracion;
	}
	public String getPais() {
		return pais;
	}
	public void setPais(String pais) {
		this.pais = pais;
	}
	public String[] getDirector() {
		return director;
	}
	public void setDirector(String[] director) {
		this.director = director;
	}
	public String getGuion() {
		return guion;
	}
	public void setGuion(String guion) {
		this.guion = guion;
	}
	public String getMusica() {
		return musica;
	}
	public void setMusica(String musica) {
		this.musica = musica;
	}
	public String getFotografia() {
		return fotografia;
	}
	public void setFotografia(String fotografia) {
		this.fotografia = fotografia;
	}
	public String[] getReparto() {
		return reparto;
	}
	public void setReparto(String[] reparto) {
		this.reparto = reparto;
	}
	public String getProductora() {
		return productora;
	}
	public void setProductora(String productora) {
		this.productora = productora;
	}
	public String getGenero() {
		return genero;
	}
	public void setGenero(String genero) {
		this.genero = genero;
	}
	public String getSinopsis() {
		return sinopsis;
	}
	public void setSinopsis(String sinopsis) {
		this.sinopsis = sinopsis;
	}
	public Map<String, String> getPremios() {
		return premios;
	}
	public void setPremios(Map<String, String> premios) {
		this.premios = premios;
	}
	public Map<String, String[]> getCriticas() {
		return criticas;
	}
	public void setCriticas(Map<String, String[]> criticas) {
		this.criticas = criticas;
	}
	public String getValoracion() {
		return valoracion;
	}
	public void setValoracion(String valoracion) {
		this.valoracion = valoracion;
	}
	public String getNotaUsuario() {
		return notaUsuario;
	}
	public void setNotaUsuario(String notaUsuario) {
		this.notaUsuario = notaUsuario;
	}
	public String getNotaAlmasGemelas() {
		return notaAlmasGemelas;
	}
	public void setNotaAlmasGemelas(String notaAlmasGemelas) {
		this.notaAlmasGemelas = notaAlmasGemelas;
	}	
}
