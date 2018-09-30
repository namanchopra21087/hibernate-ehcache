package com.hibernate.hibernate_codebase;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.hibernate.dto.UserDetails;

/**
 * Implementing hibernate with EHCache
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	SessionFactory factory=new Configuration().configure().buildSessionFactory();
    	Session session=factory.openSession();
    	UserDetails user=new UserDetails();
    	user.setName("Naman");
    	user.setAge(22);
    	try{
    		org.hibernate.Transaction tx=session.beginTransaction();
	    	//session.save(user);
	    	getFromCache(session);
	    	tx.commit();
    	}catch(Exception e){
    		e.printStackTrace();
    	}finally{
    		session.close();
    	}
    	
    }

    /**
     * Fetch value from EHChahe and is it doesn't exist than fetch from database.
     * @param session
     */
	private static void getFromCache(Session session) {
		CacheManager cm=CacheManager.getInstance();
		Cache cache=cm.getCache("namanCache");
		for(int i=0;i<2;i++){
			if(cache.get(i)!=null){
				System.out.println(((UserDetails)cache.get(1).getObjectValue()).getName());
			}else{
				UserDetails user1=(UserDetails)session.get(UserDetails.class, i);
				System.out.println(user1.getName());
				cache.put(new Element(i+1,user1));
			}
		}
		cm.shutdown();
	}
}
