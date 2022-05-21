package repos.database;

import domain.Game;
import domain.Utilizator;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import repos.GameRepository;

import java.util.ArrayList;
import java.util.List;

public class GameDBRepository implements GameRepository {
    private static SessionFactory factory;
    @Override
    public Game findOne(Integer integer) {
        return null;
    }

    @Override
    public Iterable<Game> findAll() {
        Session session = factory.openSession();
        Transaction tx = null;
        List<Game> list1 = new ArrayList<>();
        try {
            tx = session.beginTransaction();
            List list = session.createQuery("FROM Game").list();
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

    @Override
    public Game save(Game entity) {
        Session session = factory.openSession();
        Transaction tx = null;
        Integer userID = null;

        try {
            tx = session.beginTransaction();
            //Client utilizator = new Client(user, parola, nume);
            userID = (Integer) session.save(entity);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        //return userID;
        return entity;
    }

    @Override
    public void delete(Integer integer) {

    }

    @Override
    public Game update(Game entity) {
        return null;
    }

    public GameDBRepository() {
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

            GameDBRepository test = new GameDBRepository();
            //test.save(new Game());
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
