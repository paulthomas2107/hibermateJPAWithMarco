package org.example;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.Ignore;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit test for simple App.
 */
public class HibernateFullTest {

    private SessionFactory sessionFactory;

    @BeforeEach
    protected void setUp() throws Exception {
	   // A SessionFactory is set up once for an application!
	   final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
			 .configure() // configures settings from hibernate.cfg.xml
			 .build();
	   try {
		  sessionFactory = new MetadataSources( registry ).buildMetadata().buildSessionFactory();
	   }
	   catch (Exception e) {
		  // The registry would be destroyed by the SessionFactory, but we had trouble building the SessionFactory
		  // so destroy it manually.
		  StandardServiceRegistryBuilder.destroy( registry );
	   }
    }

    @AfterEach
    protected void tearDown() throws Exception {
	   if ( sessionFactory != null ) {
		  sessionFactory.close();
	   }
    }

	@Test
	void save_my_first_object() {

		User user = new User(new Long(1L), "Paul", LocalDate.now());

		try {

			Session session = sessionFactory.openSession();
			session.beginTransaction();
			session.persist(user);
			session.getTransaction().commit();

		} catch(Exception e) {
			System.out.println(e);
		}
	}

	@Test
	void hql_fetch_users() {

		try {

			Session session = sessionFactory.openSession();
			session.beginTransaction();

			List<User> users = session.createQuery("select u from User u", User.class).list();

			users.forEach(System.out::println);

			session.getTransaction().commit();

		} catch(Exception e) {
			System.out.println(e);
		}
	}

	/*
	@Ignore
    @SuppressWarnings("unchecked")
    @Test
    public void testBasicUsage() {
	   // create a couple of events...
	   Session session = sessionFactory.openSession();
	   session.beginTransaction();
	   session.remove(new User(new Long(1), "Marco's Friend", LocalDate.now()));
	   session.getTransaction().commit();
	   session.close();

	   session = sessionFactory.openSession();
	   session.beginTransaction();
	   List<User> result = session.createQuery( "select u from User u" , User.class).list();
	   for ( User user : result) {
		  System.out.println( "User (" + user.getName() + ") : " + user.getBirthDate() );
	   }
	   session.getTransaction().commit();
	   session.close();
    }

	 */

    @Test
    public void marco_is_in_the_house() {
	   assertThat(1).isGreaterThanOrEqualTo(0);
    }
}
