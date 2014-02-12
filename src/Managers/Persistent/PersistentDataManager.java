package Managers.Persistent;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.proxy.HibernateProxy;

import Managers.Manager;
import Model.Artista;
import Model.Disco;
import Model.FichaPelicula;
import Model.HelperItem;
import Model.Transmission;

public class PersistentDataManager extends Manager{
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
	
	public Transmission[] listPersistentObjects(){
		if(!initialized){
			return null;
		}
		
		List<Transmission> objects = listTransmissions();
		for (Transmission transmission : objects) {
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
		return Arrays.copyOf(objects.toArray(), objects.size(), Transmission[].class);
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
	        entity = (T) ((HibernateProxy) entity).getHibernateLazyInitializer().getImplementation();
	    }
	    return entity;
	}
	
	public boolean addTransmission(HelperItem item){
		if(!initialized){
			return false;
		}
		
		try{
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			
			Transmission t = new Transmission();
			if(item.getClass() == FichaPelicula.class){
				t.setTipoItem(Transmission.TRANSMISSION_TYPE_FILM);
			}else if(item.getClass() == Artista.class){
				t.setTipoItem(Transmission.TRANSMISSION_TYPE_ARTIST);
			}else if(item.getClass() == Disco.class){
				t.setTipoItem(Transmission.TRANSMISSION_TYPE_ALBUM);
			}
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
	
	public boolean deleteTransmission(Transmission t){
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
	
	public boolean updateTransmission(Transmission t){
		if(!initialized){
			return false;
		}
		
		try{
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			
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
