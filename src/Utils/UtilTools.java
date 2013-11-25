package Utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import org.apache.commons.lang3.StringEscapeUtils;

public class UtilTools {	
	public String readConfigFile(){
		BufferedReader bf;
		try {
			bf = new BufferedReader(new FileReader("config"));
		} catch (FileNotFoundException e) {
			return null;
		}
		String config = "";
		String sCadena;
		try {
			while ((sCadena = bf.readLine())!=null) {
			   config += sCadena;
			}
			bf.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return null;
		}
		
		return config;
	}
	   
	public String escapeSpecial(String original){
		return StringEscapeUtils.unescapeHtml4(original);
	}
	
	public String quitQuotes(String chain){
		if(chain.startsWith("\"")){
			chain = chain.substring(1);
		}
		if(chain.endsWith("\"")){
			chain = chain.substring(0, chain.length());
		}
		return chain;
	}
}
	
