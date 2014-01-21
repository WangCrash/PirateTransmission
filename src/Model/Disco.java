package Model;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;

import org.apache.commons.lang3.ArrayUtils;

public class Disco extends HelperItem {
	private String mbid;
	private String artista;
	private String nombre;
	private int año;
	private URL imageURL;
	private String[] canciones;
	private String[] tags;
	private String wikiText;
	
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
	public URL getImageURL() {
		return imageURL;
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
		return "Disco: " + this.getNombre();
	}
}
