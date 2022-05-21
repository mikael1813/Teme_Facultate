package repos.database;

import domain.Rezervare;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import repos.RezervareRepository;
import utils.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

public class RezervareDBRepository implements RezervareRepository {
    private JdbcUtils dbUtils;
    private static final Logger logger = LogManager.getLogger();


    public RezervareDBRepository(Properties props) {
        logger.info("Initializing RezervareDBRepository with properties: {} ", props);
        dbUtils = new JdbcUtils(props);
    }
    @Override
    public Rezervare findOne(Integer integer) {
        return null;
    }

    @Override
    public Iterable<Rezervare> findAll() {
        return null;
    }

    @Override
    public Rezervare save(Rezervare entity) {
        logger.traceEntry("saving entity {}", entity);
        Connection con = dbUtils.getConnection();
        try (PreparedStatement preStmt = con.prepareStatement("insert into Rezervare (IDClient, IDLoc) values (?,?)")) {
            preStmt.setInt(1, entity.getClient().getId());
            preStmt.setInt(2, entity.getLoc().getId());
            int result = preStmt.executeUpdate();
            logger.trace("Saved {} instances", result);
        } catch (SQLException ex) {
            logger.error(ex);
            System.err.println("Error DB " + ex);
        }
        logger.traceExit();
        return entity;
    }

    @Override
    public void delete(Integer integer) {

    }

    @Override
    public Rezervare update(Rezervare entity) {
        return null;
    }
}
