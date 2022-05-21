package repos.database;

import domain.Administrator;
import domain.Loc;
import domain.Spectacol;
import domain.enums.Stare;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import repos.LocRepository;
import utils.JdbcUtils;

import javax.persistence.Query;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class LocDBRepository implements LocRepository {
    private static SessionFactory factory;

    /* Method to CREATE an employee in the database */
    public Loc save(Loc loc) {
        Session session = factory.openSession();
        Transaction tx = null;
        Integer userID = null;

        try {
            tx = session.beginTransaction();
            //Client utilizator = new Client(user, parola, nume);
            userID = (Integer) session.save(loc);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        //return userID;
        return loc;
    }

    @Override
    public Loc findOne(Integer integer) {
        return null;
    }

    /* Method to  READ all the employees */
    public List<Loc> findAll() {
        Session session = factory.openSession();
        Transaction tx = null;
        List<Loc> list1 = new ArrayList<>();
        try {
            tx = session.beginTransaction();
            List list = session.createQuery("FROM Loc").list();
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


    /* Method to UPDATE salary for an employee */
    public Loc update(Loc loc) {
        Session session = factory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            Loc loc1 = (Loc) session.get(Loc.class, loc.getId());
            loc1.setLoja(loc.getLoja());
            loc1.setNumar(loc.getNumar());
            loc1.setPret(loc.getPret());
            loc1.setRand(loc.getRand());
            loc1.setStare(loc.getStare());
            session.update(loc1);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return loc;
    }

    /* Method to DELETE an employee from the records */
    public void delete(Integer ID) {
        Session session = factory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            Loc loc = (Loc) session.get(Loc.class, ID);
            session.delete(loc);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }


    public LocDBRepository() {
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

    public static void main(String[] args) {
        try {
            //initialize();

            LocDBRepository test = new LocDBRepository();
            //test.save(new Loc(1,1,3,200,"Liber"));
            System.out.println(test.findAll());
            //System.out.println(test.findAll());
        } catch (Exception e) {
            System.err.println("Exception " + e);
            e.printStackTrace();
        } finally {
            close();
        }
    }
}
