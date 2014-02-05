import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.ScrollPane;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.h2.command.dml.RunScriptCommand;
import org.h2.tools.RunScript;
import org.h2.tools.Server;
import org.hibernate.Hibernate;
import org.hibernate.Session;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import Managers.Helpers.FilmAffinityBot;
import Model.Event;
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
	
	public Connection connectWithDataBase() throws Exception{
		
        Class.forName("org.h2.Driver");
        Connection conn = DriverManager.
            //getConnection("jdbc:h2:~/test", "sa", "");
        	getConnection("jdbc:h2:target/pt_db", "pt_admin", "");
        // add application code here
        return conn;
	}
	
	public boolean isDBBuilt(Connection conn){
		try {
			ResultSet meta = conn.getMetaData().getTables(null, null, null, new String[]{"TABLE"});
			boolean result = true;
			while (meta.next()) {
				System.out.println(meta.getString(3));
				if(!meta.getString(3).equals("EVENTS")){
			    	result = false;
			    }
			}
			return result;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean buildDB(Connection conn){
		try {
			RunScript.execute(conn, new FileReader("Hibernate/db_schema.sql"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	private void createAndStoreEvent(Session session, String title, Date theDate) {
        Event theEvent = new Event();
        theEvent.setTitle(title);
        theEvent.setDate(theDate);
        session.save(theEvent);
    }
	
	
	public void saveObjectsWithHibernate(){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
		for (int i = 0; i < 10; i++) {
			createAndStoreEvent(session, "Event nº " + i, new Date());
		}
		session.getTransaction().commit();
		//HibernateUtil.getSessionFactory().close();
	}
	
	public void saveObjectWithHibernate(String title, Date date){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		
		Event event = new Event();
		event.setTitle(title);
		event.setDate(date);
		session.save(event);
		
		session.getTransaction().commit();
	}
	
	public void updateObjectWithHibernate(Event event){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		
		session.update(event);
		
		session.getTransaction().commit();
	}
	
	public List<Event> listObjectsFromDB(){
		List<Event> events = listEvents();
		System.out.println("A PUNTO DE MOSTRAR TODOS LOS OBJETOS");
        for (int i = 0; i < events.size(); i++) {
            Event theEvent = (Event) events.get(i);
            System.out.println(
                    "Event: " + theEvent.getTitle() + " Time: " + theEvent.getDate()
            );
        }
        System.out.println("TODOS LOS OBJETOS MOSTRADOS");
        return events;
	}
	
	private List<Event> listEvents() {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        List<Event> result = session.createQuery("from Event").list();
        session.getTransaction().commit();
        return result;
    }
	
	public List<FichaPelicula> listFilmsFromDB(){
		List<FichaPelicula> films = listFilms();
		System.out.println("A PUNTO DE MOSTRAR TODAS LAS PELIS");
        for (int i = 0; i < films.size(); i++) {
            FichaPelicula film = (FichaPelicula) films.get(i);
            System.out.println(film);
        }
        System.out.println("TODAS LAS PELIS MOSTRADAS");
        return films;
	}
	
	private List<FichaPelicula> listFilms(){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        List<FichaPelicula> result = session.createQuery("from FichaPelicula").list();
        session.getTransaction().commit();
        return result;
	}
	
	public void saveFilm(FichaPelicula film){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		
		session.save(film);
		
		session.getTransaction().commit();
	}
	
	public static void main(String[] args) throws Exception{
		pruebas p = new pruebas();
		Connection conn = p.connectWithDataBase();
		/*if(!p.isDBBuilt(conn)){
			if(!p.buildDB(conn)){
				return;
			}else{
				System.out.println("DB succesfully created");
			}
		}*/
		conn.close();
		//Thread.sleep(10000);
		//p.saveObjectsWithHibernate();
		//p.saveObjectWithHibernate("The fucking event", new Date());
		
//		List<Event> lista = p.listObjectsFromDB();
//		Event event = lista.get(lista.size() - 1);
//		event.setTitle("The great fucking event");
//		p.updateObjectWithHibernate(event);
		
		//p.listObjectsFromDB();
		
//		FichaPelicula film = new FichaPelicula();
//		film.setDataUcd("1141234lñkfañlkjfasdfasdfasdf");
//		film.setImageUrl("image url");
//		film.setFilmDetailsUrl("film details url");
//		film.setTitulo("titulo");
//		film.setTituloOriginal("titulo original");
//		film.setAño("el año");
//		film.setDuracion("uyy lo que duraaaa");
//		film.setPais("er pais");
//		String[] directores = new String[]{"director 1", "director 2" , "director 3"};
//		film.setDirector(directores);
//		film.setGuion("guion");
//		film.setSinopsis("que güena esta la prota");
//		p.saveFilm(film);
		
		p.listFilmsFromDB();
		
		HibernateUtil.getSessionFactory().close();
	}
}
