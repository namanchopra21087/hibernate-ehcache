package com.hibernate.hibernate_codebase;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.hibernate.dto.AsiaUsers;
import com.hibernate.dto.Classes;
import com.hibernate.dto.Department;
import com.hibernate.dto.Employee;
import com.hibernate.dto.EuropeUsers;
import com.hibernate.dto.Student;
import com.hibernate.dto.UserDetails;

/**
 * Implementing hibernate with EHCache
 *
 */
public class Main 
{
    public static void main( String[] args )
    {
    	SessionFactory factory=new Configuration().configure().buildSessionFactory();
    	Session session=factory.openSession();
    	Collection<Employee> emp=new ArrayList<Employee>();
    	try{
    		org.hibernate.Transaction tx=session.beginTransaction();
    		/**Saving entities*/
    		saveUser(session);
    		/**One To Many Associations*/
    		oneToManyAssociations(session, emp);
    		/**Many To Many Associations*/
    		manyToManyAssociations(session);
    		tx.commit();
    	}catch(Exception e){
    		e.printStackTrace();
    	}finally{
    		session.close();
    	}
    	
    }

	/**
	 * @param session
	 */
	private static void manyToManyAssociations(Session session) {
		Set<Student> studentSet=new HashSet<Student>();
		Student s1=new Student();
		s1.setStudentName("Naman");
		
		Student s2=new Student();
		s2.setStudentName("Shanker");
		studentSet.add(s1);
		studentSet.add(s2);
		
		List<Classes> classesList=new ArrayList<Classes>();
		Classes c1=new Classes();
		c1.setClassName("Science-Class");
		
		Classes c2=new Classes();
		c2.setClassName("Maths-Class");
		classesList.add(c1);
		classesList.add(c2);
		
		s1.setClasses(classesList);
		s2.setClasses(classesList);
		
		c1.setStudents(studentSet);
		c2.setStudents(studentSet);
		
		session.save(c1);
		session.save(c2);
	}

	/**
	 * @param session
	 * @param emp
	 * @param dp
	 */
	private static void oneToManyAssociations(Session session,
			Collection<Employee> emp) {
		Employee em=new Employee();
		Department dp=new Department();
		em.setName("Shanker");
		em.setSalary(BigDecimal.valueOf(10000));
		em.setDp(dp);
		emp.add(em);
		
		Employee em1=new Employee();
		em1.setName("Satya");
		em1.setSalary(BigDecimal.valueOf(10001));
		em1.setDp(dp);
		emp.add(em1);
		
		dp.setDepartmentName("IT");
		dp.setEmp(emp);
		session.save(dp);
	}

	/**
	 * @param session
	 */
	private static void saveUser(Session session) {
		UserDetails user=new UserDetails();
		user.setName("Naman");
		user.setAge(22);
		
		EuropeUsers eUser=new EuropeUsers();
		eUser.setCountry("Greece");
		
		AsiaUsers aUser=new AsiaUsers();
		aUser.setLanguage("Chinesse");
		session.save(user);
		session.save(eUser);
		session.save(aUser);
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
