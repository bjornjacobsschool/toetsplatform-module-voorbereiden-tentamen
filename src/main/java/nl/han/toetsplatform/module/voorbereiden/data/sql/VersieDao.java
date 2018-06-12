package nl.han.toetsplatform.module.voorbereiden.data.sql;

import nl.han.toetsplatform.module.shared.storage.StorageDao;
import nl.han.toetsplatform.module.voorbereiden.data.SqlLoader;
import nl.han.toetsplatform.module.voorbereiden.models.Versie;

import javax.inject.Inject;
import java.sql.*;


/**
 * This class should only be used from other classes in this package
 */
public class VersieDao {

    private StorageDao _storageDao;

    private SqlLoader _sqlLoader;

    @Inject
    public VersieDao(StorageDao _storageDao, SqlLoader sqlLoader) {
        this._storageDao = _storageDao;
        this._sqlLoader = sqlLoader;
    }

    public Versie getVersie(int id) throws SQLException {
        Connection conn = _storageDao.getConnection();

        PreparedStatement preparedStatement = conn.prepareStatement(_sqlLoader.load("select_versie"));
        preparedStatement.setInt(1, id);
        ResultSet rs = preparedStatement.executeQuery();


        while (rs.next()){
            Versie versie = new Versie();
            versie.setDatum(rs.getLong("datum"));
            versie.setOmschrijving(rs.getString("omschrijving"));
            versie.setNummer(0);
            versie.setId(rs.getInt("id"));
            return versie;
        }

       return null;
    }

    public int addVersie(Versie versie) throws SQLException {
        Connection conn = _storageDao.getConnection();

        PreparedStatement psVersie = conn.prepareStatement(_sqlLoader.load("insert_versie"));

        psVersie.setLong(1,versie.getDatum());
        psVersie.setInt(2, versie.getNummer());
        psVersie.setString(3, versie.getOmschrijving());
        psVersie.execute();

        Statement statement = conn.createStatement();
        ResultSet rsVersieId = statement.executeQuery("SELECT last_insert_rowid()");
        int versie_id = -1;
        while (rsVersieId.next()) {
            versie_id = rsVersieId.getInt(1);
        }
        rsVersieId.close();

        return versie_id;
    }
}
