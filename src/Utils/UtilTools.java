package Utils;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.apache.commons.lang3.StringEscapeUtils;

public class UtilTools {	
	private String readConfigFile(){
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
	
	private boolean writeConfigFile(String config){
		PrintWriter writer;
		try {
			writer = new PrintWriter("config");
		} catch (FileNotFoundException e) {
			System.out.println("No se ha encontrado el fichero de configuración");
			return false;
		}
		writer.println(config);
		writer.close();
		return true;
	}
	
	public Map<String, String> getConfiguration(){
		String config = this.readConfigFile();
		if(config == null){
			return null;
		}	
		Map<String, String> properties = new HashMap<String, String>();
		String paramsRegex = "(.*?):(.*?);";
		Pattern p = Pattern.compile(paramsRegex);
		Matcher m = p.matcher(config);
		while(m.find()){
			String keyParam = m.group(1);
			String valueParam = m.group(2);
			properties.put(keyParam, valueParam);
		}
		return properties;
	}
	
	public boolean setConfiguration(Map<String, String> properties){
		String config = "";
		for (Map.Entry<String, String> entry : properties.entrySet())
		{
			config += entry.getKey() + ":" + entry.getValue() + ";";
		}
		System.out.println("Configuración final: " + config);
		return this.writeConfigFile(config);
	}
	   
	public String escapeHtmlSpecialChars(String original){
		return StringEscapeUtils.unescapeHtml4(original);
	}
	
	public String encodeString(String original){
		String encoded;
		try {
			encoded = URLEncoder.encode(original, "ISO-8859-1");
		} catch (UnsupportedEncodingException e) {
			return original;
		}
		return encoded;
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
	
	public void openURLInNavigator(URL url){
		if(Desktop.isDesktopSupported())
		{
		  try {
			Desktop.getDesktop().browse(url.toURI());
			} catch (IOException e) {
				System.out.println("couldn't open that URL");
			} catch (URISyntaxException e) {
				System.out.println("couldn't open that URL");
			}
		}else{
			System.out.println("Web browser not found");
		}
	}
	
	public void showInfoOKDialog(JFrame frame, String title, String message){
		JOptionPane.showMessageDialog(frame, message, title, JOptionPane.INFORMATION_MESSAGE);
	}
	
	public void showWarningDialog(JFrame frame, String title, String message){
		JOptionPane.showMessageDialog(frame, message, title, JOptionPane.WARNING_MESSAGE);
	}
}
	
