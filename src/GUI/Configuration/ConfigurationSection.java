package GUI.Configuration;

import java.util.Map;

import javax.swing.JPanel;

public abstract class ConfigurationSection extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5590489197411203857L;
	
	public abstract Map<String, String> getChangedValues();
}
