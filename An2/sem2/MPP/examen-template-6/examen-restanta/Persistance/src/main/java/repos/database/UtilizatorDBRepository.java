package repos.database;


import domain.Utilizator;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import repos.UtilizatorRepository;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;


public class UtilizatorDBRepository implements UtilizatorRepository {
    private static SessionFactory factory;

    /* Method to CREATE an employee in the database */
    public Utilizator save(Utilizator client) {
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
    public Utilizator findOne(Integer integer) {
        return null;
    }

    /* Method to  READ all the employees */
    public List<Utilizator> findAll() {
        Session session = factory.openSession();
        Transaction tx = null;
        List<Utilizator> list1 = new ArrayList<>();
        try {
            tx = session.beginTransaction();
            List list = session.createQuery("FROM Utilizator").list();
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

    public Utilizator findByUser(String username) {

        Query query = factory.createEntityManager().createQuery("FROM Utilizator u where u.user=:user");
        query.setParameter("user", username);
        //query.setParameter("parola", password);

        Utilizator u = (Utilizator) query.getSingleResult();
        return u;
    }

    /* Method to UPDATE salary for an employee */
    public Utilizator update(Utilizator client) {
        Session session = factory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            Utilizator user = (Utilizator) session.get(Utilizator.class, client.getId());
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
            Utilizator client = (Utilizator) session.get(Utilizator.class, userID);
            session.delete(client);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Autowired
    public UtilizatorDBRepository() {
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

            UtilizatorDBRepository test = new UtilizatorDBRepository();
            //test.save("asa","123","Petru");
            System.out.println(test.findAll());
            //System.out.println(test.findAll());
        } catch (Exception e) {
            System.err.println("Exception " + e);
            e.printStackTrace();
        } finally {
            //close();
        }
    }
}
