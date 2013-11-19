package Utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

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
}
