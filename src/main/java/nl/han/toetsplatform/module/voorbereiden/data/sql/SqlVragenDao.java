package nl.han.toetsplatform.module.voorbereiden.data.sql;

import nl.han.toetsplatform.module.shared.storage.StorageDao;
import nl.han.toetsplatform.module.voorbereiden.data.SqlLoader;
import nl.han.toetsplatform.module.voorbereiden.data.VragenDao;
import nl.han.toetsplatform.module.voorbereiden.models.Tentamen;
import nl.han.toetsplatform.module.voorbereiden.models.Versie;
import nl.han.toetsplatform.module.voorbereiden.models.Vraag;

import javax.inject.Inject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SqlVragenDao implements VragenDao {

    private final static Logger LOGGER = Logger.getLogger(SqlVragenDao.class.getName());

    private SqlLoader _sqlLoader;

    private StorageDao _storageDao;

    private VersieDao _versieDao;

    @Inject
    public SqlVragenDao(SqlLoader _sqlLoader, StorageDao _storageDao, VersieDao versieDao) {
        this._sqlLoader = _sqlLoader;
        this._storageDao = _storageDao;
        this._versieDao = versieDao;
    }

    @Override
    public void insertVraag(Vraag vraag) {
        Connection conn = _storageDao.getConnection();


        try {
            int id = _versieDao.addVersie(vraag.getVersieVersie());
            System.out.println(id);
            vraag.setVersie(_versieDao.getVersie(id));

            PreparedStatement ps = conn.prepareStatement(_sqlLoader.load("insert_vraag"));
            ps.setString(1, vraag.getId().toString());
            ps.setString(2, vraag.getNaam());
            ps.setString(3, vraag.getVraagtype());
            ps.setString(4, vraag.getThema());
            ps.setInt(5, vraag.getPunten());
            ps.setInt(6, vraag.getVersieVersie().getId());
            ps.setString(7, vraag.getNakijkInstructies());
            ps.setString(8, vraag.getVraagData());
            ps.setString(9, vraag.getNakijkModel());
            System.out.println(ps.execute());

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Could not insert vraag: " + e.getMessage());
        }
        _storageDao.closeConnection();
    }

    public void insertTentamenVraag(Tentamen tentamen, Vraag vraag){
        Connection conn = _storageDao.getConnection();

        try {
            PreparedStatement ps = conn.prepareStatement(_sqlLoader.load("insert_tentamen_vraag"));
            ps.setString(1, vraag.getId().toString());
            ps.setInt(2, vraag.getVersieVersie().getId());
            ps.setString(3, tentamen.getId().toString());
            ps.setInt(4, tentamen.getVersie().getId());
            ps.execute();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Could not insert tentamen vraag: " + e.getMessage());
        }
    }

    public List<Vraag> getVragen(){
        Connection conn = _storageDao.getConnection();
        if(conn == null) return new ArrayList<>();

        try
        {
            List<Vraag> vragen = new ArrayList<>();
            System.out.println("Tentamen vraag");
            PreparedStatement ps = conn.prepareStatement(_sqlLoader.load("select_vraag"));
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Vraag vraag = new Vraag();
                vraag.setId(UUID.fromString(rs.getString("id")));
                vraag.setNaam(rs.getString("naam"));
                vraag.setThema(rs.getString("thema"));
                vraag.setVraagData(rs.getString("vraag_type"));
                vraag.setVraagData(rs.getString("vraag_data"));
                Versie versie = _versieDao.getVersie(rs.getInt("versie_id"));
                //vraag.setVersieVersie(versie);
                vragen.add(vraag);
            }
            rs.close();
            return vragen;
        }
        catch (SQLException e){

        }
        return new ArrayList<>();
    }
}
