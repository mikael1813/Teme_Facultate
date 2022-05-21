package repos.database;

import domain.Client;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import repos.ClientRepository;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

public class ClientDBRepository implements ClientRepository {
    /*private JdbcUtils dbUtils;
    private static final Logger logger = LogManager.getLogger();


    public ClientDBRepository(Properties props) {
        logger.info("Initializing ClientDBRepository with properties: {} ", props);
        dbUtils = new JdbcUtils(props);
    }

    @Override
    public Client findOne(Integer integer) {
        return null;
    }

    @Override
    public Iterable<Client> findAll() {
        return null;
    }

    @Override
    public Client save(Client entity) {
        return null;
    }

    @Override
    public void delete(Integer integer) {

    }

    @Override
    public Client update(Client entity) {
        return null;
    }

    @Override
    public Client findByUser(String user) {
        logger.traceEntry("selecting entity {}", user);
        Connection con = dbUtils.getConnection();
        Client client1 = new Client("","");

        try (PreparedStatement preStmt = con.prepareStatement("select * from Client where User=?")) {
            preStmt.setString(1, user);
            ResultSet result = preStmt.executeQuery();
            int id1 = result.getInt("ID");
            String parola = result.getString("Password");
            String nume = result.getString("Nume");
            Client client = new Client(user, parola,nume);
            client.setId(id1);
            client1 = client;
            logger.trace("Selected {} instance", result);
        } catch (SQLException ex) {
            logger.error(ex);
            System.err.println("Error DB " + ex);
        }
        logger.traceExit();
        return client1;
    }
    */
    /////


    private static SessionFactory factory;

    /* Method to CREATE an employee in the database */
    public Client save(Client client) {
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
    public Client findOne(Integer integer) {
        return null;
    }

    /* Method to  READ all the employees */
    public List<Client> findAll() {
        Session session = factory.openSession();
        Transaction tx = null;
        List<Client> list1 = new ArrayList<>();
        try {
            tx = session.beginTransaction();
            List list = session.createQuery("FROM Client").list();
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

    public Client findByUser(String username) {

        Query query = factory.createEntityManager().createQuery("FROM Client u where u.user=:user");
        query.setParameter("user", username);
        //query.setParameter("parola", password);

        Client u = (Client) query.getSingleResult();
        return u;
    }

    /* Method to UPDATE salary for an employee */
    public Client update(Client client) {
        Session session = factory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            Client user = (Client) session.get(Client.class, client.getId());
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
            Client client = (Client) session.get(Client.class, userID);
            session.delete(client);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }


    public ClientDBRepository() {
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

            ClientDBRepository test = new ClientDBRepository();
            //test.save("asa","123","Petru");
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
