package hibernate;

import domain.User;
import domain.Utilizator;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ManageUtilizator {
    private static SessionFactory factory;

    /* Method to CREATE an employee in the database */
    public Integer add(String user, String parola){
        Session session = factory.openSession();
        Transaction tx = null;
        Integer userID = null;

        try {
            tx = session.beginTransaction();
            Utilizator utilizator = new Utilizator(user, parola);
            userID = (Integer) session.save(utilizator);
            tx.commit();
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return userID;
    }

    /* Method to  READ all the employees */
    public List <Utilizator> findAll( ){
        Session session = factory.openSession();
        Transaction tx = null;
        List <Utilizator> list1 = new ArrayList<>();
        try {
            tx = session.beginTransaction();
            List list = session.createQuery("FROM Utilizator").list();
            list1 = list;
            tx.commit();
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
            return list1;
        }
    }

    public Utilizator findBy(String username, String password) {

        Query query = factory.createEntityManager().createQuery("from Utilizator u where u.user=:user and u.parola=:parola");
        query.setParameter("user", username);
        query.setParameter("parola", password);

        Utilizator u = (Utilizator) query.getSingleResult();
        return u;
    }

    /* Method to UPDATE salary for an employee */
    public void update(Integer userID,String username,String parola){
        Session session = factory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            Utilizator user = (Utilizator)session.get(Utilizator.class, userID);
            user.setParola( parola );
            user.setUser(username);
            session.update(user);
            tx.commit();
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    /* Method to DELETE an employee from the records */
    public void delete(Integer userID){
        Session session = factory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            Utilizator utilizator = (Utilizator)session.get(Utilizator.class, userID);
            session.delete(utilizator);
            tx.commit();
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }


    public void initialize() {
        // A SessionFactory is set up once for an application!
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure() // configures settings from hibernate.cfg.xml
                .build();
        try {
            factory = new MetadataSources( registry ).buildMetadata().buildSessionFactory();
        }
        catch (Exception e) {
            System.err.println("Exception "+e);
            StandardServiceRegistryBuilder.destroy( registry );
            e.printStackTrace();
        }
    }

    static void close(){
        if ( factory != null ) {
            factory.close();

        }

    }

    public static void main(String[] args) {
        try {
            //initialize();

            //ManageUtilizator test = new ManageUtilizator();
            //System.out.println(test.findOne("aa","b"));
            //System.out.println(test.findAll());
        }catch (Exception e){
            System.err.println("Exception "+e);
            e.printStackTrace();
        }finally {
            close();
        }
    }
}
