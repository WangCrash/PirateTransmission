package Utils;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

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
	   
	public String escapeHtmlSpecialChars(String original){
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
	
	public void openURLInNavigator(URL url){
		if(Desktop.isDesktopSupported())
		{
		  try {
			Desktop.getDesktop().browse(url.toURI());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("couldn't open that URL");
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
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
	
