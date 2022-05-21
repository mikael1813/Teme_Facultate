package repos.database;

import domain.Cuvant;
import domain.Utilizator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import repos.RepositoryCuvant;
import utils.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class CuvantDBRepository implements RepositoryCuvant {
    private static SessionFactory factory;
    private JdbcUtils dbUtils;

    private static final Logger logger = LogManager.getLogger();

    /* Method to CREATE an employee in the database */
    public Cuvant save(Cuvant loc) {
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
    public Cuvant findOne(Integer integer) {
        return null;
    }

    /* Method to  READ all the employees */
    public List<Cuvant> findAll() {
        Session session = factory.openSession();
        Transaction tx = null;
        List<Cuvant> list1 = new ArrayList<>();
        try {
            tx = session.beginTransaction();
            List list = session.createQuery("FROM Cuvant").list();
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
//    @Override
//    public Iterable<Cuvant> findAll() {
//        logger.traceEntry();
//        Connection con = dbUtils.getConnection();
//        List<Cuvant> cuvinte = new ArrayList<>();
//        try (PreparedStatement preStmt = con.prepareStatement("select * from Cuvant")) {
//            try (ResultSet result = preStmt.executeQuery()) {
//                while (result.next()) {
//                    int id = result.getInt("ID");
//                    String cuv = result.getString("cuvant");
//                    String atr1 = result.getString("carac1");
//                    String atr2 = result.getString("carac2");
//                    Cuvant cuvant = new Cuvant(cuv,atr1,atr2);
//                    cuvant.setId(id);
//
//                    cuvinte.add(cuvant);
//                }
//            }
//        } catch (SQLException e) {
//            logger.error(e);
//            System.err.println("Error DB " + e);
//        }
//        logger.traceExit(cuvinte);
//        return cuvinte;
//    }


    /* Method to UPDATE salary for an employee */
    public Cuvant update(Cuvant loc) {
        Session session = factory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            Cuvant loc1 = (Cuvant) session.get(Cuvant.class, loc.getId());
            loc1.setCuvant(loc.getCuvant());
            loc1.setCarac1(loc.getCarac1());
            loc1.setCarac2(loc.getCarac2());
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
            Cuvant loc = (Cuvant) session.get(Cuvant.class, ID);
            session.delete(loc);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Override
    public Cuvant update(Integer integer, Cuvant entity) {
        return null;
    }


    public CuvantDBRepository() {

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

            CuvantDBRepository test = new CuvantDBRepository();
            //test.save(new Cuvant("copac","verde","maro"));
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
