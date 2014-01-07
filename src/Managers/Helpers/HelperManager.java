package Managers.Helpers;

import java.util.Map;

import Managers.Manager;
import Model.HelperItem;

public abstract class HelperManager extends Manager{
	
	public abstract boolean isLogged();
	
	public abstract HelperItem[] getRecommendations(Map<String, String> filters);
	
	public abstract boolean rateItem(HelperItem item);
	
	public abstract HelperItem[] searchItem(String search, int option);
	
	public abstract String getHelperName();
}
