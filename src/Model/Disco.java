package Model;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Calendar;

import org.apache.commons.lang3.ArrayUtils;

import de.umass.lastfm.Album;
import de.umass.lastfm.ImageSize;

public class Disco extends HelperItem {
	private String mbid;
	private String artista;
	private String nombre;
	private int año;
	private String discografica;
	private URL imageURL;
	private String[] canciones;
	private String[] tags;
	private String wikiText;
	
	public Disco(){
		
	}
	
	public Disco(Album album){
		this.setMbid(album.getMbid());
		this.setNombre(album.getName());
		this.setArtista(album.getArtist());
		if(album.getReleaseDate() != null){
			Calendar cal = Calendar.getInstance();
			cal.setTime(album.getReleaseDate());
			this.setAño(cal.get(Calendar.YEAR));
		}
		this.setImageURL(album.getImageURL(ImageSize.LARGE));
	}
	
	public String getArtista() {
		return artista;
	}
	public void setArtista(String artista) {
		this.artista = artista;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public int getAño() {
		return año;
	}
	public void setAño(int año) {
		this.año = año;
	}
	public String getDiscografica() {
		return discografica;
	}
	public void setDiscografica(String discografica) {
		this.discografica = discografica;
	}
	public URL getImageURL() {
		return imageURL;
	}
	public void setImageURL(URL imageURL){
		this.imageURL = imageURL;
	}
	public void setImageURL(String imageURLString) {
		try {
			this.imageURL = new URL(imageURLString);
		} catch (MalformedURLException e) {
			System.out.println("bad image URL");
			this.imageURL = null;
		}
	}
	public String[] getCanciones() {
		return canciones;
	}
	public void setCanciones(String[] canciones) {
		this.canciones = canciones;
	}
	public String getMbid() {
		return mbid;
	}
	public void setMbid(String mbid) {
		this.mbid = mbid;
	}
	public String[] getTags() {
		return tags;
	}
	public String[] getNFirstTags(int n){
		int lenght = tags.length;
		if(lenght < n){
			n = lenght;
		}
		return Arrays.copyOf(this.getTags(), n);
	}
	public void setTags(String[] tags) {
		this.tags = tags;
	}
	public String getWikiText() {
		return wikiText;
	}
	public void setWikiText(String wikiText) {
		this.wikiText = wikiText;
	}
	
	@Override
	public String toString(){
		String result = "Disco: " + getNombre() + "\n";
		if(getMbid() != null){
			result += "Mbid" + getMbid() + "\n"; 
		}
		if(getImageURL() != null){
			result += "Image: " + getImageURL() + "\n";
		}
		if(getArtista() != null){
			result += "Artista: " + getArtista() + "\n";
		}
		if(getAño() != 0){
			result += "Año: " + getAño() + "\n";
		}
		if(getTags() != null){
			String tags = "";
			for (int i = 0; i < getTags().length; i++) {
				tags += getTags()[i] + ", ";
			}
			tags = tags.substring(0, tags.length() - 2);
			result += "Tags: " + tags + "\n";
		}
		return result;
	}
	
	@Override
	public boolean getIsRated() {
		return isRated;
	}

	@Override
	public void setIsRated(boolean isRated) {
		this.isRated = isRated;
	}
}
