import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.ScrollPane;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import Managers.Helpers.FilmAffinityBot;
import Model.FichaPelicula;


public class pruebas {
	public void filmAffinityTesting(){
		boolean initiated = FilmAffinityBot.getInstance().initManager();
		
		if(initiated){
			//FichaPelicula[] result = FilmAffinityBot.getListRecommendations();
			//FichaPelicula[] result = FilmAffinityBot.getListRecommendations(FilmAffinityBot.FAMS_GENRE_KEY_ACTION);
			FichaPelicula[] result = FilmAffinityBot.getInstance().searchItem("star trek", FilmAffinityBot.FILMAFFINITY_TITLE_SEARCH_OPTION);
			if(result == null){
				System.out.println("no hay resultados");
				return;
			}else{
				System.out.println("Resultados: " + result.length);
			}
			for (int i = 0; i < result.length; i++) {
				System.out.println("------- " + i + " --------");
				System.out.println(result[i]);
				if(i == 2){
					//result[i] = FilmAffinityBot.fillFichaPelicula(result[i]);
					System.out.println(result[i]);
					/*if(FilmAffinityBot.voteForFilm(result[i], "7")){
						System.out.println("Voted!");
						System.out.println(result[i]);
					}*/
					break;
				}
			}
			
			boolean terminated = FilmAffinityBot.getInstance().terminateManager();
			if(terminated)
				System.out.println("\nPROCESS CORRECTLY COMPLETED");
		}
		/*FilmAffinityBot.initializeManager(true);
		FichaPelicula[]resultado = FilmAffinityBot.searchFilm("batman");
		if(resultado != null){
			System.out.println("Encontrados " + resultado.length + " resultados");
			for (int i = 0; i < resultado.length; i++) {
				System.out.println(resultado[i]);
			}
		}*/
	}
	public void scrollPaneTesting(){
		JFrame frame = new JFrame("Probando scroll");
		frame.setSize(300,  300);
		frame.setVisible(true);
		frame.setLayout(new BorderLayout());
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0, 1));
		JScrollPane scroll = new  JScrollPane();
		scroll.setViewportView(panel);
		for (int i = 0; i < 30; i++) {
			panel.add(new JButton("Botón " + i));
		}
		frame.add(scroll);
		frame.revalidate();
		Rectangle visible = scroll.getViewport().getVisibleRect();
		scroll.getViewport().scrollRectToVisible(new Rectangle(visible.x, 150, visible.width, visible.height));
		System.out.println(visible);
		System.out.println(scroll.getViewport().getBounds());
		System.out.println(scroll.getVerticalScrollBar().getValue());
		
	}
	public static void main(String[] args) throws Exception{
		new pruebas().scrollPaneTesting();
	}
}
