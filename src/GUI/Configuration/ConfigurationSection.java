package GUI.Configuration;

import java.util.Map;

import javax.swing.JPanel;

import Managers.Manager;

@SuppressWarnings("serial")
public abstract class ConfigurationSection extends JPanel{
	protected ConfigView parentView;
	protected Manager manager;
	
	public ConfigurationSection(ConfigView parentView){
		this.parentView = parentView;
	}
	
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
