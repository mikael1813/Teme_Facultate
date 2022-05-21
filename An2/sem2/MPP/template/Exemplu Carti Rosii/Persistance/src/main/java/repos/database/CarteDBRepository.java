package repos.database;

import domain.Carte;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import repos.CarteRepository;

import java.util.ArrayList;
import java.util.List;

public class CarteDBRepository implements CarteRepository {
    private static SessionFactory factory;

    /* Method to CREATE an employee in the database */
    public Carte save(Carte loc) {
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
    public Carte findOne(Integer integer) {
        return null;
    }

    /* Method to  READ all the employees */
    public List<Carte> findAll() {
        Session session = factory.openSession();
        Transaction tx = null;
        List<Carte> list1 = new ArrayList<>();
        try {
            tx = session.beginTransaction();
            List list = session.createQuery("FROM Carte").list();
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
    public Carte update(Carte loc) {
        Session session = factory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            Carte loc1 = (Carte) session.get(Carte.class, loc.getId());
            loc1.setNumar(loc.getNumar());
            loc1.setCuloare(loc.getCuloare());
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
            Carte loc = (Carte) session.get(Carte.class, ID);
            session.delete(loc);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }


    public CarteDBRepository() {
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

            CarteDBRepository test = new CarteDBRepository();
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
