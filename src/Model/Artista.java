package Model;

import java.net.MalformedURLException;
import java.net.URL;

public class Artista extends HelperItem {
	private String mbid;
	private String nombre;
	private Disco[] discografia;
	private URL imageURL;
	
	public void setNombre(String nombre){
		this.nombre = nombre;
	}
	public String getNombre(){
		return nombre;
	}
	public Disco[] getDiscografia() {
		return discografia;
	}
	public void setDiscografia(Disco[] discografia) {
		this.discografia = discografia;
	}
	
	@Override
	public String toString(){
		return getNombre();
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
	public String getMbid() {
		return mbid;
	}
	public void setMbid(String mbid) {
		this.mbid = mbid;
	}
}
