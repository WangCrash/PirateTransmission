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
import java.util.prefs.Preferences;

import org.h2.command.dml.RunScriptCommand;
import org.h2.tools.RunScript;
import org.h2.tools.Server;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.proxy.HibernateProxy;

import de.umass.lastfm.Artist;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import Managers.Helpers.FilmAffinityBot;
import Managers.Persistent.HibernateUtil;
import Managers.Persistent.PersistentDataManager;
import Model.Artista;
import Model.Disco;
import Model.Transmission;
import Model.FichaPelicula;
import Model.HelperItem;


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
	
	private void createAndStoreEvent(Session session, String itemType, Date theDate) {
        Transmission theEvent = new Transmission();
        theEvent.setTipoItem(itemType);
        theEvent.setFecha(theDate);
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
	
	public Transmission saveObjectWithHibernate(String itemType, Date date, HelperItem item){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		
		Transmission t = new Transmission();
		t.setTipoItem(itemType);
		t.setFecha(date);
		t.setHelperItem(item);
		session.save(item);
		session.save(t);
		
		session.getTransaction().commit();
		
		return t;
	}
	
	public void deleteObjectWithHibernate(Transmission event){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		
		HelperItem helperItem = event.getHelperItem();
		session.delete(event);
		session.delete(helperItem);		
		
		session.getTransaction().commit();
	}
	
	public void updateObjectWithHibernate(Transmission event){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		
		session.update(event);
		
		//session.getTransaction().commit();
	}
	
	public void listAllObjectsFromDB(){
		List<Transmission> objects = listTransmissions();
		for (Transmission listEvent : objects) {
			System.out.println("ITEM: ");
			if(listEvent.getHelperItem().getClass() == FichaPelicula.class){
				FichaPelicula eventFilm = (FichaPelicula)listEvent.getHelperItem();
				System.out.println(eventFilm);
			}else if(listEvent.getHelperItem().getClass() == Artista.class){
				Artista eventArtist = (Artista)listEvent.getHelperItem();
				System.out.println(eventArtist);
			}else if(listEvent.getHelperItem().getClass() == Disco.class){
				Disco eventAlbum = (Disco)listEvent.getHelperItem();
				System.out.println(eventAlbum);
			}
		}
	}
	
	private List<Transmission> listTransmissions() {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        List<Transmission> result = session.createQuery("from Transmission").list();
        for (Transmission t : result) {
			t.setHelperItem(initializeAndUnproxy(t.getHelperItem()));
		}
        session.getTransaction().commit();
        return result;
    }
	
	private static <T> T initializeAndUnproxy(T entity) {
	    if (entity == null) {
	        throw new 
	           NullPointerException("Entity passed for initialization is null");
	    }

	    Hibernate.initialize(entity);
	    if (entity instanceof HibernateProxy) {
	        entity = (T) ((HibernateProxy) entity).getHibernateLazyInitializer()
	                .getImplementation();
	    }
	    return entity;
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
	
	public void saveItem(HelperItem film){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		
		session.save(film);
		
		session.getTransaction().commit();
	}
	
	public static void main(String[] args) throws Exception{
		if(!PersistentDataManager.getInstance().initManager()){
			System.out.println("PERSISTENT MANAGER NO INICIADO");
			return;
		}
		
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
		
		//Transmission peli = PersistentDataManager.getInstance().addTransmission(film);//p.saveObjectWithHibernate("una peliculita bonita", new Date(), film);
		
//		Artista artista = new Artista();
//		artista.setMbid("asdfasdfasfd");
//		artista.setImageURL("http://www.image.com");
//		artista.setNombre("The testing band");
//		artista.setTags(new String[]{"tag 1", "tag 2", "tag 3"});
		
		//Transmission artistilla = PersistentDataManager.getInstance().addTransmission(artista);//p.saveObjectWithHibernate("un artistita", new Date(), artista);
		
//		Disco album = new Disco();
//		album.setMbid("asdfasdfasdf");
//		album.setImageURL("otra imagen que no va a valer");
//		album.setNombre("A Fucking Test Album");
//		album.setArtista("The testing band");
//		album.setAño(1995);
//		album.setTags(new String[]{"tag 4", "tag 5", "tag 6"});
		
		//Transmission disquito = PersistentDataManager.getInstance().addTransmission(album);//p.saveObjectWithHibernate("un disquito", new Date(), album);
		
		//System.out.println("Deleting peli");
		//PersistentDataManager.getInstance().deleteTransmission(peli);
		
		PersistentDataManager.getInstance().listPersistentObjects();
		
		//System.out.println("PELIS");
		//p.listFilmsFromDB();
//		List<Event> lista = p.listObjectsFromDB();
//		Event event = lista.get(lista.size() - 1);
//		event.setTitle("The great fucking event");
//		p.updateObjectWithHibernate(event);
		
		//p.listFilmsFromDB();
		
		PersistentDataManager.getInstance().finalizeManager();
		
		//Preferences.userRoot().node(pruebas.class.getName());
	}
}
