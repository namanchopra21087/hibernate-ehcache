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

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cache.ehcache.EhCacheRegionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.hibernate.constants.DtoConstants;
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
    		//saveUser(session);
    		/**One To Many Associations*/
    		//oneToManyAssociations(session, emp);
    		/**Many To Many Associations*/
    		//manyToManyAssociations(session);
    		/**HQL example */
    		//hqlQuery(session);
    		/**Named Queries*/
    		//namedQuery(session);
    		/**Criteria Query*/
    		//criteriaQuery(session);
    		/**Projections in criteria query */
    		//criteriaQueryWithProjections(session);
    		tx.commit();
    		/**Second level cache*/
    		session = secondLevelCacheHibernate(factory, session);
    		/**Query level cache*/
    		session = queryLevelCache(factory, session);
    	}catch(Exception e){
    		e.printStackTrace();
    	}finally{
    		session.close();
    	}
    }

	/**
	 * Query level caching.
	 * <li>Set below property for enabling this feature in hinernate.cfg.xml</li>
	 * <li><property name="hibernate.cache.use_query_cache">true</property></li>
	 * @param factory
	 * @param session
	 * @return session
	 */
	@SuppressWarnings(value=DtoConstants.SUPPRESS_ALL_WARNINGS)
	private static Session queryLevelCache(SessionFactory factory,
			Session session) {
		Query query=session.createQuery("from Employee e where e.employeeId=6");
		query.setCacheable(true);
		query.list();
		session.close();
		session=factory.openSession();
		query=session.createQuery("from Employee e where e.employeeId=6");
		query.setCacheable(true);
		query.list();
		return session;
	}

	/**
	 * Hibernate second level caching.
	 * <li>Set below property for enabling this feature in hinernate.cfg.xml</li>
	 * <li>1) <property name="hibernate.cache.use_second_level_cache">true</property></li>
	 * <li>2) <property name="hibernate.cache.use_query_cache">true</property></li>
	 * <li>3) <property name="hibernate.cache.region.factory_class">org.hibernate.cache.ehcache.EhCacheRegionFactory</property></li>
	 * <li>4) <property name="hibernate.javax.cache.provide">net.sf.ehcache.hibernate.EhCacheProvider</property></li>
	 * @param factory
	 * @param session
	 * @return session
	 */
	private static Session secondLevelCacheHibernate(SessionFactory factory,
			Session session) {
		session.close();
		session=factory.openSession();
		session.get(Employee.class, Long.valueOf(7));
		session.close();
		session=factory.openSession();
		session.get(Employee.class, Long.valueOf(7));
		return session;
	}

	/**
	 * @param session
	 */
	private static void criteriaQueryWithProjections(Session session) {
		int departmentId=300;
		Criteria query=session.createCriteria(Employee.class,"e")
				.createAlias("e.dp", "eDp")
				.setProjection(Projections.property("salary"))
				.add(Restrictions.lt("eDp.departmentId", Long.valueOf(departmentId)))
				.addOrder(Order.desc("e.name"));
		/**Below query properties used for pagination */
		query.setFirstResult(0);
		query.setMaxResults(10);
		List<BigDecimal> empList=(List<BigDecimal>)query.list();
		int i=1;
		System.out.println("Projections inside criteria query result for query with department id");
		for(BigDecimal e:empList){
			System.out.println(i+++" Salary:"+e);
		}
		
		/**Another criteria query mapping with projections*/
		ProjectionList prjList=Projections.projectionList();
		prjList.add(Projections.property("name"));
		prjList.add(Projections.property("salary"));
		query=session.createCriteria(Employee.class,"e")
				.createAlias("e.dp", "eDp")
				.setProjection(prjList)
				.add(Restrictions.lt("eDp.departmentId", Long.valueOf(departmentId)))
				.addOrder(Order.desc("e.name"));
		/**Below query properties used for pagination */
		query.setFirstResult(0);
		query.setMaxResults(10);
		List<Object[]> empListObj=(List<Object[]>)query.list();
		i=1;
		System.out.println("Projections inside criteria query result for query with department id");
		for(Object[] obj:empListObj){
			System.out.println(i+++") Name:"+obj[0]+" Salary:"+obj[1]);
		}
	}

	/**
	 * Criteria query
	 * @param session
	 */
	private static void criteriaQuery(Session session) {
		int departmentId=300;
		Criteria query=session.createCriteria(Employee.class,"e")
				.createAlias("e.dp", "eDp")
				.add(Restrictions.lt("eDp.departmentId", Long.valueOf(departmentId)))
				.addOrder(Order.desc("e.name"));
		/**Below query properties used for pagination */
		query.setFirstResult(0);
		query.setMaxResults(10);
		List<Employee> empList=(List<Employee>)query.list();
		int i=1;
		System.out.println("Criteria query result for query with department id");
		for(Employee e:empList){
			System.out.println(i+++") Name:"+e.getName()+" Salary:"+e.getSalary());
		}
		/**Another criteria query mapping*/
		query=session.createCriteria(Employee.class,"e")
				.createAlias("e.dp", "eDp")
				//.add(Restrictions.between("eDp.departmentId", Long.valueOf(100), Long.valueOf(400)))
				.add(Restrictions.or(Restrictions.eq("eDp.departmentId", Long.valueOf(100)),Restrictions.eq("eDp.departmentId", Long.valueOf(400))))
				.addOrder(Order.desc("e.name"))
				.setFirstResult(0)
				.setMaxResults(10);
		/**Below query properties used for pagination */
		empList=(List<Employee>)query.list();
		i=1;
		System.out.println("Another criteria query mapping");
		for(Employee e:empList){
			System.out.println(i+++") Name:"+e.getName()+" Salary:"+e.getSalary());
		}
	}

	/**
	 * Get named query for employee class.
	 * @param session
	 */
	private static void namedQuery(Session session) {
		int departmentId=300;
		Query query=session.getNamedQuery(DtoConstants.EMPLOYEE_DEP_ID_NQ);
		query.setInteger(DtoConstants.DEPARTMENT_ID, departmentId);
		/**Below query properties used for pagination */
		query.setFirstResult(0);
		query.setMaxResults(10);
		List<Employee> empList=(List<Employee>)query.list();
		int i=1;
		System.out.println("Named Query result for query with department id");
		for(Employee e:empList){
			System.out.println(i+++") Name:"+e.getName()+" Salary:"+e.getSalary());
		}
		
		int employeeId=5;
		query=session.getNamedQuery(DtoConstants.EMPLOYEE_ID_NQ);
		query.setInteger(DtoConstants.EMP_ID, employeeId);
		/**Below query properties used for pagination */
		query.setFirstResult(0);
		query.setMaxResults(10);
		empList=(List<Employee>)query.list();
		i=1;
		System.out.println("Named Query result for query with employee id");
		for(Employee e:empList){
			System.out.println(i+++") Name:"+e.getName()+" Salary:"+e.getSalary());
		}
		
		employeeId=10;
		query=session.getNamedQuery(DtoConstants.EMPLOYEE_ID_NNQ);
		query.setInteger(DtoConstants.EMP_ID, employeeId);
		/**Below query properties used for pagination */
		query.setFirstResult(0);
		query.setMaxResults(10);
		empList=(List<Employee>)query.list();
		i=1;
		System.out.println("Named Native Query result for query with employee id");
		for(Employee e:empList){
			System.out.println(i+++") Name:"+e.getName()+" Salary:"+e.getSalary());
		}
	}

	/**
	 * <li>Create HQL query inside hibernate and fetch the results.</li>
	 * <li>Used pagination in it.</li>
	 * @param session
	 */
	private static void hqlQuery(Session session) {
		int departmentId=300;
		Query query=session.createQuery("from Employee e where e.dp.departmentId<=:"+DtoConstants.DEPARTMENT_ID+" order by e.name desc");
		query.setInteger(DtoConstants.DEPARTMENT_ID, departmentId);
		/**Below query properties used for pagination */
		query.setFirstResult(0);
		query.setMaxResults(10);
		List<Employee> empList=(List<Employee>)query.list();
		int i=1;
		for(Employee e:empList){
			System.out.println(i+++") Name:"+e.getName()+" Salary:"+e.getSalary());
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
		em.setName("Sebastian");
		em.setSalary(BigDecimal.valueOf(10000));
		em.setDp(dp);
		emp.add(em);
		
		Employee em1=new Employee();
		em1.setName("Charlie");
		em1.setSalary(BigDecimal.valueOf(10001));
		em1.setDp(dp);
		emp.add(em1);
		
		dp.setDepartmentName("Security");
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
		session.update(user);
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
