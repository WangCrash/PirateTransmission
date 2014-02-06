package Model;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;

import de.umass.lastfm.Artist;
import de.umass.lastfm.ImageSize;

public class Artista extends HelperItem {
	private String mbid;
	private String nombre;
	private Disco[] discografia;
	private URL imageURL;
	private Artista[] similares;
	private String[] tags;
	private String bio;
	
	public Artista(){	
	}
	
	public Artista(Artist artist){
		this.setMbid(artist.getMbid());
		this.setNombre(artist.getName());
		this.setImageURL(artist.getImageURL(ImageSize.LARGE));
	}
	
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
	public String getMbid() {
		return mbid;
	}
	public void setMbid(String mbid) {
		this.mbid = mbid;
	}
	public Artista[] getSimilares() {
		return similares;
	}
	public void setSimilares(Artista[] similares) {
		this.similares = similares;
	}
	public String[] getTags() {
		return tags;
	}
	public String[] getNFirstTags(int n){
		int length = tags.length;
		if(length < n){
			n = length;
		}
		return Arrays.copyOf(this.getTags(), n);
	}
	public void setTags(String[] tags) {
		this.tags = tags;
	}
	public String getBio() {
		return bio;
	}
	public void setBio(String bio) {
		this.bio = bio;
	}

	@Override
	public String toString(){
		String result = "Artista: " + getNombre() + "\n";
		if(getMbid() != null){
			result += "Mbid: " + getMbid() + "\n";
		}
		if(getImageURL() != null){
			result += "image: " + getImageURL() + "\n";
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
