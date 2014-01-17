package Managers.Helpers;

import java.util.Map;

import Managers.Manager;
import Model.HelperItem;

public abstract class HelperManager extends Manager{
	
	public static String HELPERMANAGER_NAME_FILMS = "FilmAffnity";
	public static String HELPERMANAGER_NAME_MUSIC = "LastFM";
	
	public abstract boolean isLogged();
	
	public abstract HelperItem[] getRecommendations(Map<String, String> filters);
	
	public abstract String getUserConfigKey();
	
	public abstract String getPasswordConfigKey();
	
	public abstract boolean rateItem(HelperItem item);
	
	public abstract HelperItem[] searchItem(String search, int option);
	
	public abstract String getHelperName();
	
	public abstract int getDefaultSearchOption();
	
	public abstract Map<String, Integer> getSearchOptions();
	
}
