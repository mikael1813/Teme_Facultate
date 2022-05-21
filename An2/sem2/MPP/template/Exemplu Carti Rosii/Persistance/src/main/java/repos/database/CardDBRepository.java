package repos.database;

import domain.Card;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.springframework.stereotype.Component;
import repos.CardRepositoy;
import utils.JdbcUtils;

import javax.persistence.Query;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Component
public class CardDBRepository implements CardRepositoy {
    private JdbcUtils dbUtils;

    private static final Logger logger = LogManager.getLogger();

    public CardDBRepository(Properties props) {
        logger.info("Initializing UtilizatorDBRepository with properties: {} ", props);
        dbUtils = new JdbcUtils(props);
    }


    @Override
    public Card findOne(Integer integer) {
        return null;
    }

    @Override
    public Iterable<Card> findAll() {
        logger.traceEntry();
        Connection con = dbUtils.getConnection();
        List<Card> probas = new ArrayList<>();
        try (PreparedStatement preStmt = con.prepareStatement("select * from Card")) {
            try (ResultSet result = preStmt.executeQuery()) {
                while (result.next()) {
                    int id = result.getInt("id");
                    String culoare = result.getString("culoare");
                    int nr = result.getInt("nr");
                    Card proba = new Card(culoare, nr);
                    proba.setId(id);
                    probas.add(proba);
                }
            }
        } catch (SQLException e) {
            logger.error(e);
            System.err.println("Error DB " + e);
        }
        logger.traceExit(probas);
        return probas;
    }

    @Override
    public Card save(Card entity) {
        return null;
    }

    @Override
    public void delete(Integer integer) {

    }

    @Override
    public Card update(Card entity) {
        return null;
    }
}
