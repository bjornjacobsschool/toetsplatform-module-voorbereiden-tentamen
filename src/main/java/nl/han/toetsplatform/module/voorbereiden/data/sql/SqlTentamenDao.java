package nl.han.toetsplatform.module.voorbereiden.data.sql;

import nl.han.toetsapplicatie.apimodels.dto.SamengesteldTentamenDto;
import nl.han.toetsapplicatie.apimodels.dto.VersieDto;
import nl.han.toetsapplicatie.apimodels.dto.VragenbankVraagDto;
import nl.han.toetsapplicatie.apimodels.dto.KlaargezetTentamenDto;
import nl.han.toetsapplicatie.apimodels.dto.SamengesteldTentamenDto;
import nl.han.toetsplatform.module.shared.storage.StorageDao;
import nl.han.toetsplatform.module.voorbereiden.data.SqlLoader;
import nl.han.toetsplatform.module.voorbereiden.data.TentamenDao;
import nl.han.toetsplatform.module.voorbereiden.data.VragenDao;
import nl.han.toetsplatform.module.voorbereiden.models.KlaargezetTentamen;

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


    private VragenDao _vragenDao;

    @Inject
    public SqlTentamenDao(StorageDao storageDao, SqlLoader sqlLoader, VragenDao vragenDao) {
        this._storageDao = storageDao;
        this._sqlLoader = sqlLoader;
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
    public void saveTentamen(SamengesteldTentamenDto tentamen) {
        Connection conn = _storageDao.getConnection();
        if(!isDatabaseConnected(conn))  return;


        PreparedStatement psVersie = null;
        try {

            PreparedStatement psTentamen = conn.prepareStatement(_sqlLoader.load("insert_tentamen"));
            psTentamen.setString(1, tentamen.getId().toString());
            psTentamen.setString(2, tentamen.getNaam());
            psTentamen.setString(3, tentamen.getBeschrijving());
            psTentamen.setString(4, tentamen.getToegestaandeHulpmiddelen());
            psTentamen.setString(5, tentamen.getVak());
            psTentamen.setInt(6, tentamen.getVersie().getNummer());
            psTentamen.setLong(7, tentamen.getVersie().getDatum());
            psTentamen.setString(8, tentamen.getVersie().getOmschrijving());
            psTentamen.execute();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Could not save data to database: " + e.getMessage());
        }

        for(VragenbankVraagDto vraag : tentamen.getVragen()){
            _vragenDao.insertTentamenVraag(tentamen, vraag);
        }
    }

    @Override
    public List<SamengesteldTentamenDto> loadTentamens() {
        Connection conn = _storageDao.getConnection();
        if(!isDatabaseConnected(conn))  return new ArrayList<>();

        try {
            ResultSet rs = conn.createStatement().executeQuery(_sqlLoader.load("select_tentamen"));
            List<SamengesteldTentamenDto> tentamens = new ArrayList<>();

            while (rs.next()){
                SamengesteldTentamenDto tentamen = new SamengesteldTentamenDto();
                tentamen.setId(UUID.fromString(rs.getString("id")));
                tentamen.setNaam(rs.getString("naam"));
                tentamen.setBeschrijving(rs.getString("beschrijving"));
                tentamen.setToegestaandeHulpmiddelen(rs.getString("toegestaandeHulpmiddelen"));
                tentamen.setVak(rs.getString("vak"));
                tentamen.setTijdsduur("90 minuten");
                VersieDto versie = new VersieDto();
                versie.setNummer(rs.getInt("versie_nummer"));
                versie.setDatum(rs.getLong("versie_datum"));
                versie.setOmschrijving(rs.getString("versie_omschrijving"));
                tentamen.setVersie(versie);
                tentamen.setVragen(_vragenDao.getVragenVanTentamen(tentamen));
                tentamens.add(tentamen);
            }
            rs.close();
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
