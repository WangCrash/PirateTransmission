package error;

import java.util.HashMap;
import java.util.Map;


public class ErrorDescription {
	public static int NO_INTERNET_CONNECTION = 0;
	
	private static Map<Integer, String> descriptionsMap = initializeDescriptions();
	
	public String descriptionByCode(int code){
		return descriptionsMap.get(code);
	}
	
	private static Map<Integer, String> initializeDescriptions(){
		descriptionsMap = new HashMap<Integer, String>();
		descriptionsMap.put(NO_INTERNET_CONNECTION, "No hay conexión a internet");
		return descriptionsMap;
	}
}
