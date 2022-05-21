package repos.database;

import domain.Round;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import repos.RoundRepository;
import utils.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Component
public class RoundDBRepository implements RoundRepository {
    private JdbcUtils dbUtils;

    private static final Logger logger = LogManager.getLogger();

    public RoundDBRepository(Properties props) {
        logger.info("Initializing UtilizatorDBRepository with properties: {} ", props);
        dbUtils = new JdbcUtils(props);
    }


    @Override
    public Round findOne(Integer integer) {
        return null;
    }

    @Override
    public Iterable<Round> findAll() {
        logger.traceEntry();
        Connection con = dbUtils.getConnection();
        List<Round> probas = new ArrayList<>();
        try (PreparedStatement preStmt = con.prepareStatement("select * from Round")) {
            try (ResultSet result = preStmt.executeQuery()) {
                while (result.next()) {
                    int id = result.getInt("id");
                    int i1 = result.getInt("puncte1");
                    int i2 = result.getInt("puncte2");
                    int i3 = result.getInt("puncte3");
                    String model1 = result.getString("model1");
                    String model2 = result.getString("model2");
                    String model3 = result.getString("model3");
                    String propunere1 = result.getString("propunere1");
                    String propunere2 = result.getString("propunere2");
                    String propunere3 = result.getString("propunere3");
                    int game = result.getInt("game");
                    Round proba = new Round(model1,model2,model3,propunere1,propunere2,propunere3,i1,i2,i3);
                    proba.setGame(game);
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
    public Round save(Round entity) {
        Round proba = (Round) entity;
        logger.traceEntry("saving entity {}", entity);
        Connection con = dbUtils.getConnection();
        try (PreparedStatement preStmt = con.prepareStatement("insert into Round (model1,model2,model3,propunere1,propunere2,propunere3,puncte1,puncte2,puncte3) values (?,?,?,?,?,?,?,?,?)")) {
            preStmt.setString(1, proba.getModel1());
            preStmt.setString(2, proba.getModel2());
            preStmt.setString(3, proba.getModel3());
            preStmt.setString(4, proba.getPropunere1());
            preStmt.setString(5, proba.getPropunere2());
            preStmt.setString(6, proba.getPropunere3());
            preStmt.setInt(7, proba.getPuncte1());
            preStmt.setInt(8, proba.getPuncte2());
            preStmt.setInt(9, proba.getPuncte3());
            int result = preStmt.executeUpdate();
            logger.trace("Saved {} instances", result);
        } catch (SQLException ex) {
            logger.error(ex);
            System.err.println("Error DB " + ex);
        }
        logger.traceExit();
        return proba;
    }

    @Override
    public void delete(Integer integer) {

    }

    @Override
    public Round update(Round entity) {
        return null;
    }
}
