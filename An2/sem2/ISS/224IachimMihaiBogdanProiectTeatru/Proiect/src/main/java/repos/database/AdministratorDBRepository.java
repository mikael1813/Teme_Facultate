package repos.database;

import domain.Administrator;
import domain.Client;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import repos.AdministratorRepository;
import utils.JdbcUtils;

import javax.persistence.Query;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class AdministratorDBRepository implements AdministratorRepository {
    private static SessionFactory factory;

    /* Method to CREATE an employee in the database */
    public Administrator save(Administrator client) {
        Session session = factory.openSession();
        Transaction tx = null;
        Integer userID = null;

        try {
            tx = session.beginTransaction();
            //Client utilizator = new Client(user, parola, nume);
            userID = (Integer) session.save(client);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        //return userID;
        return client;
    }

    @Override
    public Administrator findOne(Integer integer) {
        return null;
    }

    /* Method to  READ all the employees */
    public List<Administrator> findAll() {
        Session session = factory.openSession();
        Transaction tx = null;
        List<Administrator> list1 = new ArrayList<>();
        try {
            tx = session.beginTransaction();
            List list = session.createQuery("FROM Administrator").list();
            list1 = list;
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
            return list1;
        }
    }

    public Administrator findByUser(String username) {

        Query query = factory.createEntityManager().createQuery("FROM Administrator u where u.user=:user");
        query.setParameter("user", username);
        //query.setParameter("parola", password);

        Administrator u = (Administrator) query.getSingleResult();
        return u;
    }

    /* Method to UPDATE salary for an employee */
    public Administrator update(Administrator client) {
        Session session = factory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            Administrator user = (Administrator) session.get(Administrator.class, client.getId());
            user.setParola(client.getParola());
            user.setUser(client.getUser());
            user.setNume(client.getNume());
            session.update(user);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return client;
    }

    /* Method to DELETE an employee from the records */
    public void delete(Integer userID) {
        Session session = factory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            Administrator client = (Administrator) session.get(Administrator.class, userID);
            session.delete(client);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }


    public AdministratorDBRepository() {
        // A SessionFactory is set up once for an application!
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure() // configures settings from hibernate.cfg.xml
                .build();
        try {
            factory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        } catch (Exception e) {
            System.err.println("Exception " + e);
            StandardServiceRegistryBuilder.destroy(registry);
            e.printStackTrace();
        }
    }

    static void close() {
        if (factory != null) {
            factory.close();

        }

    }
}
