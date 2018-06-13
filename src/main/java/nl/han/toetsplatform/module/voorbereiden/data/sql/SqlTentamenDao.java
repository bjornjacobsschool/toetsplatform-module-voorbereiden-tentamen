package nl.han.toetsplatform.module.voorbereiden.data.sql;

import nl.han.toetsapplicatie.apimodels.dto.KlaargezetTentamenDto;
import nl.han.toetsapplicatie.apimodels.dto.SamengesteldTentamenDto;
import nl.han.toetsplatform.module.shared.storage.StorageDao;
import nl.han.toetsplatform.module.voorbereiden.data.SqlLoader;
import nl.han.toetsplatform.module.voorbereiden.data.TentamenDao;
import nl.han.toetsplatform.module.voorbereiden.data.VragenDao;
import nl.han.toetsplatform.module.voorbereiden.models.KlaargezetTentamen;
import nl.han.toetsplatform.module.voorbereiden.models.Tentamen;
import nl.han.toetsplatform.module.voorbereiden.models.Versie;
import nl.han.toetsplatform.module.voorbereiden.models.Vraag;

import javax.inject.Inject;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SqlTentamenDao implements TentamenDao {
    private final static Logger LOGGER = Logger.getLogger(SqlTentamenDao.class.getName());

    private StorageDao _storageDao;

    private SqlLoader _sqlLoader;

    private VersieDao _versieDao;

    private VragenDao _vragenDao;

    @Inject
    public SqlTentamenDao(StorageDao storageDao, SqlLoader sqlLoader, VersieDao versieDao, VragenDao vragenDao) {
        this._storageDao = storageDao;
        this._sqlLoader = sqlLoader;
        this._versieDao = versieDao;
        this._vragenDao = vragenDao;
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

        tentamen.setId(UUID.randomUUID().toString());


        PreparedStatement psVersie = null;
        try {
            int versie_id = _versieDao.addVersie(new Versie());
            tentamen.setVersie(_versieDao.getVersie(versie_id));

            PreparedStatement psTentamen = conn.prepareStatement(_sqlLoader.load("insert_tentamen"));
            psTentamen.setString(1, tentamen.getId());
            psTentamen.setString(2, tentamen.getNaam());
            psTentamen.setString(3, tentamen.getBeschrijving());
            psTentamen.setString(4, tentamen.getToegestaandeHulpmiddelen());
            psTentamen.setString(5, tentamen.getVak());
            psTentamen.setInt(6, tentamen.getVersie().getId());
            psTentamen.execute();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Could not save data to database");
        }

        for(Vraag vraag : tentamen.getVragen()){
            System.out.println("Vraag osplaan");
            _vragenDao.insertVraag(vraag);
            _vragenDao.insertTentamenVraag(tentamen, vraag);
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

    @Override
    public void setTentamenKlaar(KlaargezetTentamenDto tentamen) {

    }

//    @Override
//    public void setTentamenKlaar(KlaargezetTentamen tentamen) {
//        Connection conn = _storageDao.getConnection();
//        if(!isDatabaseConnected(conn))return;
//
//        try{
//            PreparedStatement ps = conn.prepareStatement(_sqlLoader.load("insert_klaargezet_tentamen"));
//            ps.setString(1, String.valueOf(tentamen.getTentamen().getId()));
//            ps.setInt(2, tentamen.getTentamen().getVersie().getNummer());
//            ps.setDate(3,  new Date(tentamen.getVan().getTime()));
//            ps.setDate(4,  new Date(tentamen.getTot().getTime()));
//            ps.setString(5, tentamen.getSleutel());
//            ps.execute();
//        }
//        catch (SQLException e){
//            LOGGER.log(Level.SEVERE, "Could not insert klaargezet tentamen: " + e.getMessage());
//        }
//    }
}
