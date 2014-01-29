package Utils;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
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

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

import org.apache.commons.lang3.StringEscapeUtils;

public class UtilTools {	
	private String[] filmAffinityWords;
	
	public UtilTools(){
		filmAffinityWords = new String[]{"\\(\\s*?Serie de TV\\s*?\\)"};
	}
	
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
			createFileConfig();
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
	
	private void createFileConfig() {
		File config = new File("config");
		try {
			config.createNewFile();
		} catch (IOException e) {
			System.out.println(e.getMessage());
			return;
		}
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
			chain = chain.substring(0, chain.length() - 1);
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
	
	public boolean showYesNoDialog(JFrame frame, String title, String message){
		Object[] options = {"Sí", "No"};
		return (JOptionPane.showOptionDialog(frame, 
				message, 
				title, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, 
				null,     //do not use a custom Icon
				options,  //the titles of buttons
				options[0]) == JOptionPane.YES_OPTION); //default button title
	}
	
	//quita palabras que filmaffinity pone en los títulos de las fichas como aclaración y que provocan búsquedas fallidas en pirateBay
	public String killFilmAffinityWords(String corruptedWord){
		String cleanedWord = corruptedWord;
		for (int i = 0; i < filmAffinityWords.length; i++) {
			cleanedWord = cleanedWord.replaceAll(filmAffinityWords[i], "");
		}
		return cleanedWord;
	}
	
	public ImageIcon getScaledImage(Image srcImg, int w, int h){
		BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
	    Graphics2D g2 = resizedImg.createGraphics();
	    g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
	    g2.drawImage(srcImg, 0, 0, w, h, null);
	    g2.dispose();
	    return new ImageIcon(resizedImg);
	}
	
	public void setToolTipText(JComponent component, String text){
		if((text != null) && !text.isEmpty()){
			component.setToolTipText(text);
		}
	}
}
	
