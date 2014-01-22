package Managers;

public abstract class Manager {
	
	public abstract boolean isStarted();

	public abstract boolean initManager();
	
	protected abstract void setUpManager();
}
