package GUI;

public abstract class TorrentSearchThread implements Runnable {
	private String search;
	
	public TorrentSearchThread(String search){
		this.setSearch(search);
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}
}
