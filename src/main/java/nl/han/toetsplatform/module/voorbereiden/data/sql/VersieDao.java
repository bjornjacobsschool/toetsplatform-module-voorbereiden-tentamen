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

        Versie versie = new Versie();
        while (rs.next()){

            versie.setDatum(rs.getString("datum"));
            versie.setOmschrijving(rs.getString("omschrijving"));
            versie.setNumber(rs.getString("number"));
            versie.setId(rs.getInt("id"));
        }

        return versie;
    }

    public int addVersie(Versie versie) throws SQLException {
        Connection conn = _storageDao.getConnection();

        PreparedStatement psVersie = conn.prepareStatement(_sqlLoader.load("insert_versie"));

        psVersie.setString(1, versie.getDatum());
        psVersie.setString(2, versie.getNumber());
        psVersie.setString(3, versie.getOmschrijving());
        psVersie.execute();

        Statement statement = conn.createStatement();
        ResultSet rsVersieId = statement.executeQuery("SELECT last_insert_rowid()");
        int versie_id = -1;
        while (rsVersieId.next()) {
            versie_id = rsVersieId.getInt(1);
        }

        return versie_id;
    }
}
