package Model;

public abstract class HelperItem {
	protected boolean isRated;
	
	public HelperItem(){
		isRated = false;
	}

	public boolean isRated() {
		return isRated;
	}

	public void setRated(boolean isRated) {
		this.isRated = isRated;
	}
}
