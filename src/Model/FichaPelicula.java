package Model;

import java.util.Map;

import Managers.Helpers.FilmAffinityBot;

public class FichaPelicula extends HelperItem{
	public static final String MAP_KEY_PREMIOS_AÑO = "año";
	public static final String MAP_KEY_PREMIOS_PREMIO = "premio";
	public static final String MAP_KEY_CRITICAS_TEXTO = "texto";
	public static final String MAP_KEY_CRITICAS_AUTOR_REVISTA = "autor_revista";
	
	public static final int FICHAPELICULA_CRITICA_POSITIVA = 0;
	public static final int FICHAPELICULA_CRITICA_NEUTRA = 1;
	public static final int FICHAPELICULA_CRITICA_NEGATIVA = 2;
	public static final int FICHAPELICULA_CRITICA_NO_DETERMINADA = -1;
	
	private String dataUcd;
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
	private String[] premios;
	private Map<String, String[]> criticas;
	private String valoracionFA;
	private String notaUsuario;
	private String notaAlmasGemelas;

	public String getDataUcd() {
		return dataUcd;
	}
	public void setDataUcd(String dataUcd) {
		this.dataUcd = dataUcd;
	}
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
	public String[] getPremios() {
		return premios;
	}
	public void setPremios(String[] premios) {
		this.premios = premios;
	}
	public Map<String, String[]> getCriticas() {
		return criticas;
	}
	public void setCriticas(Map<String, String[]> criticas) {
		this.criticas = criticas;
	}
	
	public String getCritica(String autor){
		String[] criticaYValoracion = this.getCriticas().get(autor);
		return criticaYValoracion[0];
	}
	
	public int getValoracionCritica(String autor){
		String[] criticaYValoracion = this.getCriticas().get(autor);
		String valoracion = criticaYValoracion[1];
		if(valoracion.equals("crítica positiva")){
			return FICHAPELICULA_CRITICA_POSITIVA;
		}else if(valoracion.equals("crítica negativa")){
			return FICHAPELICULA_CRITICA_NEGATIVA;
		}else if(valoracion.equals("crítica neutra")){
			return FICHAPELICULA_CRITICA_NEUTRA;
		}
		return FICHAPELICULA_CRITICA_NO_DETERMINADA;
	}
	
	public String getValoracion() {
		return valoracionFA;
	}
	public void setValoracion(String valoracion) {
		this.valoracionFA = valoracion;
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
	
	@Override
	public String toString(){
		String result = "";
		if(this.titulo != null){
			result += "Título: " + this.getTitulo() + "\n";
		}
		if(this.getFilmDetailsUrl() != null){
			result += "Enlace: " + this.getFilmDetailsUrl() + "\n";
		}
		if(this.tituloOriginal != null){
			result += "Título original: " + this.getTituloOriginal() + "\n";
		}
		if(this.imageUrl != null){
			result += "Image: " + this.getImageUrl() + "\n";
		}
		if(this.año != null){
			result += "Año: " + this.getAño() + "\n";
		}
		if(this.duracion != null){
			result += "Duración: " + this.getDuracion() + "\n";
		}
		if(this.pais != null){
			result += "País: " + this.getPais() + "\n";
		}
		if((this.director != null) && (this.director.length > 0)){
			String directores = "";
			for (int i = 0; i < this.getDirector().length; i++) {
				directores += this.getDirector()[i] + ", ";
			}
			directores = directores.substring(0, directores.length() - 2);
			result += "Director: " + directores + "\n";
		}
		if(this.guion != null){
			result += "Guión: " + this.getGuion() + "\n";
		}
		if(this.musica != null){
			result += "Música: " + this.getMusica() + "\n";
		}
		if(this.fotografia != null){
			result += "Fotografía: " + this.getFotografia() + "\n";
		}
		if((this.reparto != null) && (this.reparto.length > 0)){
			String actores = "";
			for (int i = 0; i < this.getReparto().length; i++) {
				actores += this.getReparto()[i] + ", ";
			}
			actores = actores.substring(0, actores.length() - 2);
			result += "Reparto: " + actores + "\n";
		}
		if(this.productora != null){
			result += "Productora: " + this.getProductora() + "\n";
		}
		if(this.genero != null){
			result += "Género: " + this.getGenero() + "\n";
		}
		if(this.sinopsis != null){
			result += "Sinopsis: " + this.getSinopsis() + "\n";
		}
		if((this.premios != null) && this.premios.length > 0){
			String premios = "";
			for (int i = 0; i < this.getPremios().length; i++) {
				premios += "\t" +  this.getPremios()[i] + "\n";
			}
			result += "Premios: \n" + premios + "\n";
		}
		if(this.criticas != null){
			String listaCriticas = "";
			for (Map.Entry<String, String[]> entry : this.getCriticas().entrySet())
			{
				String autor = entry.getKey();
				String critica = entry.getValue()[0];
				String valoracion = entry.getValue()[1];
			    listaCriticas += "\t" + critica + "\n " + autor + "\n" + valoracion + "\n";
			}
			if(!listaCriticas.isEmpty()){
				result += "Críticas: \n" + listaCriticas + "\n";
			}
		}
		if(this.valoracionFA != null){
			result += "Nota: " + this.getValoracion() + "\n";
		}
		if(this.notaUsuario != null){
			result += "Tu voto: " + this.getNotaUsuario() + "\n";
		}
		if(this.notaAlmasGemelas != null){
			result += "Tus almas gemelas le ponen un " + this.getNotaAlmasGemelas() + " a esta película\n";
		}
		return result;
	}
	
	@Override
	public boolean getIsRated() {
		if(notaUsuario == null || notaUsuario.isEmpty() || notaUsuario.equals(FilmAffinityBot.FILMAFFINITY_FILM_NOT_WATCHED)){
			return false;
		}else{
			return true;
		}
	}

	@Override
	public void setIsRated(boolean isRated) {
		this.isRated = isRated;
	}
}
