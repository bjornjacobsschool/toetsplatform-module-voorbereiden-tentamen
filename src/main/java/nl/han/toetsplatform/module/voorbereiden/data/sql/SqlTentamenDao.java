package nl.han.toetsplatform.module.voorbereiden.data.sql;

import nl.han.toetsplatform.module.shared.storage.StorageDao;
import nl.han.toetsplatform.module.voorbereiden.data.SqlLoader;
import nl.han.toetsplatform.module.voorbereiden.data.TentamenDao;
import nl.han.toetsplatform.module.voorbereiden.models.Tentamen;
import nl.han.toetsplatform.module.voorbereiden.models.Versie;

import javax.inject.Inject;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SqlTentamenDao implements TentamenDao {
    private final static Logger LOGGER = Logger.getLogger(SqlTentamenDao.class.getName());

    StorageDao _storageDao;

    SqlLoader _sqlLoader;

    VersieDao _versieDao;

    @Inject
    public SqlTentamenDao(StorageDao storageDao, SqlLoader sqlLoader, VersieDao versieDao) {
        this._storageDao = storageDao;
        this._sqlLoader = sqlLoader;
        this._versieDao = versieDao;
    }

    private boolean isDatabaseConnected(Connection connection){
        if (connection == null) {
            LOGGER.log(Level.WARNING, "No database connected");
            return false;
        }

        return true;
    }

    @Override
    public void saveTentamen(Tentamen tentamen) {
        Connection conn = _storageDao.getConnection();
        if(!isDatabaseConnected(conn))  return;

        PreparedStatement psVersie = null;
        try {
            int versie_id = _versieDao.addVersie(new Versie());

            PreparedStatement psTentamen = conn.prepareStatement(_sqlLoader.load("insert_tentamen"));
            psTentamen.setString(1, UUID.randomUUID().toString());
            psTentamen.setString(2, tentamen.getNaam());
            psTentamen.setString(3, tentamen.getBeschrijving());
            psTentamen.setString(4, tentamen.getToegestaandeHulpmiddelen());
            psTentamen.setString(5, tentamen.getVak());
            psTentamen.setInt(6, versie_id);
            psTentamen.execute();
            _storageDao.closeConnection();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Could not save data to database");
        }
    }

    @Override
    public List<Tentamen> loadTentamens() {
        Connection conn = _storageDao.getConnection();
        if(!isDatabaseConnected(conn))  return new ArrayList<>();

        try {
            ResultSet rs = conn.createStatement().executeQuery(_sqlLoader.load("select_tentamen"));
            List<Tentamen> tentamens = new ArrayList<>();

            while (rs.next()){
                Tentamen tentamen = new Tentamen();
                tentamen.setId(rs.getString("id"));
                tentamen.setNaam(rs.getString("naam"));
                tentamen.setBeschrijving(rs.getString("beschrijving"));
                tentamen.setToegestaandeHulpmiddelen(rs.getString("toegestaandeHulpmiddelen"));
                tentamen.setVak(rs.getString("vak"));
                tentamen.setVersie(_versieDao.getVersie(rs.getInt("versie_id")));

                tentamens.add(tentamen);
            }
            return tentamens;

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Could not load tentamens from database: " + e.getMessage());
        }
        return new ArrayList<>();
    }
}
