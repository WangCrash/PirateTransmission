package Managers.Persistent;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.proxy.HibernateProxy;

import Managers.Manager;
import Model.Artista;
import Model.Disco;
import Model.FichaPelicula;
import Model.HelperItem;
import Model.Transmision;

public class PersistentDataManager extends Manager{
	private final int FICHAPELICULA_ITEM_TYPE = 0;
	private final int ARTISTA_ITEM_TYPE = 1;
	private final int ALBUM_ITEM_TYPE = 2;
	private static PersistentDataManager instance = null;
	
	
	private boolean initialized;
	
	private PersistentDataManager(){
		initialized = false;
	}
	
	public static PersistentDataManager getInstance(){
		synchronized (PersistentDataManager.class) {
			if(instance == null){
				instance = new PersistentDataManager();
			}
		}
		return instance;
	}

	@Override
	public boolean isStarted() {
		return initialized;
	}

	@Override
	public boolean initManager() {
		try{
			HibernateUtil.getSessionFactory();
			initialized = true;
		}catch(Exception exc){
			initialized = false;
		}
		return initialized;
	}

	@Override
	protected void setUpManager() {
		
	}
	
	public Transmision[] listPersistentObjects(){
		if(!initialized){
			return null;
		}
		
		List<Transmision> objects = listTransmissions();
		for (Transmision transmission : objects) {
			System.out.println("ITEM: ");
			System.out.println(transmission);
			if(transmission.getHelperItem().getClass() == FichaPelicula.class){
				FichaPelicula eventFilm = (FichaPelicula)transmission.getHelperItem();
				System.out.println(eventFilm);
			}else if(transmission.getHelperItem().getClass() == Artista.class){
				Artista eventArtist = (Artista)transmission.getHelperItem();
				System.out.println(eventArtist);
			}else if(transmission.getHelperItem().getClass() == Disco.class){
				Disco eventAlbum = (Disco)transmission.getHelperItem();
				System.out.println(eventAlbum);
			}
		}
		return Arrays.copyOf(objects.toArray(), objects.size(), Transmision[].class);
	}
	
	private List<Transmision> listTransmissions() {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        List<Transmision> result = session.createQuery("from Transmision order by fecha desc").list();
        for (Transmision t : result) {
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
	        entity = (T) ((HibernateProxy) entity).getHibernateLazyInitializer().getImplementation();
	    }
	    return entity;
	}
	
	public boolean addTransmission(HelperItem item){
		if(!initialized){
			return false;
		}
		
		try{
			String itemType = "";
			boolean alreadyExists = false;
			if(item.getClass() == FichaPelicula.class){
				FichaPelicula film = (FichaPelicula)item;
				alreadyExists = itemAlreadyExists(film.getTitulo(), FICHAPELICULA_ITEM_TYPE);
				itemType = Transmision.TRANSMISSION_TYPE_FILM;
			}else if(item.getClass() == Artista.class){
				Artista artist = (Artista)item;
				alreadyExists = itemAlreadyExists(artist.getNombre(), ARTISTA_ITEM_TYPE);
				itemType = Transmision.TRANSMISSION_TYPE_ARTIST;
			}else if(item.getClass() == Disco.class){
				Disco album = (Disco)item;
				alreadyExists = itemAlreadyExists(album.getNombre(), album.getArtista(), ALBUM_ITEM_TYPE);
				itemType = Transmision.TRANSMISSION_TYPE_ALBUM;
			}
			if(alreadyExists){
				return true;
			}
			
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			
			Transmision t = new Transmision();
			t.setTipoItem(itemType);
			t.setFecha(new Date());
			t.setHelperItem(item);
			t.setRated(false);
			session.save(item);
			session.save(t);
			
			session.getTransaction().commit();
			
			return true;
		}catch(Exception exc){
			return false;
		}
	}
	
	private boolean itemAlreadyExists(String name, int itemType){
		return itemAlreadyExists(name, null, itemType);
	}
	
	private boolean itemAlreadyExists(String name, String secondName,int itemType){
		try{
			boolean result = false;
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			
			String query;
			switch (itemType) {
			case FICHAPELICULA_ITEM_TYPE:	
				query = "select titulo from FichaPelicula where titulo like '" + name + "'";
				break;
			case ARTISTA_ITEM_TYPE:
				query = "select nombre from Artista where nombre like '" + name + "'";
				break;
			case ALBUM_ITEM_TYPE:
				query = "select artista from Disco where nombre like '" + name + "'";
				break;
			default:
				query = "";
				break;
			}
			
			System.out.println("QUERY: " + query);
			Query matches = session.createQuery(query);
			System.out.println("RESULT: " + matches);
			
			switch (itemType) {
			case FICHAPELICULA_ITEM_TYPE:
			case ARTISTA_ITEM_TYPE:
				result = matches.list().contains(name);
				break;
			case ALBUM_ITEM_TYPE:
				result = matches.list().contains(secondName);
				break;
			default:
				break;
			}
			
			session.getTransaction().commit();
			
			return result;
		}catch(Exception exc){
			exc.printStackTrace();
			return false;
		}
	}
	
	public boolean deleteTransmission(Transmision t){
		if(!initialized){
			return false;
		}
		try{
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.beginTransaction();
		
			HelperItem helperItem = t.getHelperItem();
			session.delete(t);
			session.delete(helperItem);		
			
			session.getTransaction().commit();
			
			return true;
		}catch(Exception exc){
			return false;
		}
	}
	
	public boolean updateTransmission(Transmision t){
		if(!initialized){
			return false;
		}
		
		try{
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			
			session.update(t.getHelperItem());
			session.update(t);
			
			session.getTransaction().commit();
			
			return true;
		}catch(Exception exc){
			return false;
		}
	}
	
	public boolean finalizeManager(){
		initialized = false;
		try{
			HibernateUtil.getSessionFactory().close();
			return true;
		}catch(Exception exc){
			System.out.println("Error: couldn't end session");
			return false;
		}
	}
}
