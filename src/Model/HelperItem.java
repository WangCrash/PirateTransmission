package Model;

public abstract class HelperItem {
	protected long id;
	protected boolean isRated;
	
	public HelperItem(){
		isRated = false;
	}

	public long getId() {
		return id;
	}

	protected void setId(long id) {
		this.id = id;
	}

	public abstract boolean getIsRated();

	public abstract void setIsRated(boolean isRated);
}
