package GUI.Configuration;

import java.util.Map;

import javax.swing.JPanel;

import Managers.Manager;

public abstract class ConfigurationSection extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5590489197411203857L;
	protected Manager manager;
	
	public abstract boolean isValidPassLength();
	public abstract Map<String, String> getChangedValues();
	public Manager getManager(){
		return manager;
	}
	public void setManager(Manager manager){
		this.manager = manager;
	}
	
	protected boolean isValidPassLength(String password){
		return password.length() >= 4;
	}
}
