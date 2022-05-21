package repos.database;

import domain.Spectacol;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import repos.SpectacolRepository;

import javax.persistence.Query;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class SpectacolDBRepository implements SpectacolRepository {
    private static SessionFactory factory;

    /* Method to CREATE an employee in the database */
    public Spectacol save(Spectacol spectacol) {
        Session session = factory.openSession();
        Transaction tx = null;
        Integer userID = null;

        try {
            tx = session.beginTransaction();
            //Client utilizator = new Client(user, parola, nume);
            userID = (Integer) session.save(spectacol);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        //return userID;
        return spectacol;
    }

    @Override
    public Spectacol findOne(Integer integer) {
        return null;
    }

    /* Method to  READ all the employees */
    public List<Spectacol> findAll() {
        Session session = factory.openSession();
        Transaction tx = null;
        List<Spectacol> list1 = new ArrayList<>();
        try {
            tx = session.beginTransaction();
            List list = session.createQuery("FROM Spectacol").list();
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
    public Spectacol update(Spectacol spectacol) {
        Session session = factory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            Spectacol spectacol1 = (Spectacol) session.get(Spectacol.class, spectacol.getId());
            spectacol1.setDate(spectacol.getDate());
            spectacol1.setNume(spectacol.getNume());

            session.update(spectacol1);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return spectacol;
    }

    /* Method to DELETE an employee from the records */
    public void delete(Integer ID) {
        Session session = factory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            Spectacol spectacol = (Spectacol) session.get(Spectacol.class, ID);
            session.delete(spectacol);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }


    public SpectacolDBRepository() {
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

            SpectacolDBRepository test = new SpectacolDBRepository();
            //test.save(new Loc(1,1,3,200,Stare.Liber));
            System.out.println(test.findAll());
            //System.out.println(test.findAll());
        } catch (Exception e) {
            System.err.println("Exception " + e);
            e.printStackTrace();
        } finally {
            close();
        }
    }

    @Override
    public Spectacol filterByDate() {
        LocalDate date = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String string = date.format(formatter);
        Query query = factory.createEntityManager().createQuery("FROM Spectacol s where s.date=:date");
        query.setParameter("date", string);
        //query.setParameter("parola", password);

        Spectacol u = (Spectacol) query.getSingleResult();
        return u;
    }
}
