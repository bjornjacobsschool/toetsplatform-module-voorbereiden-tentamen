package nl.han.toetsplatform.module.voorbereiden.data.sql;

import nl.han.toetsapplicatie.apimodels.dto.SamengesteldTentamenDto;
import nl.han.toetsapplicatie.apimodels.dto.VersieDto;
import nl.han.toetsapplicatie.apimodels.dto.VragenbankVraagDto;
import nl.han.toetsplatform.module.shared.storage.StorageDao;
import nl.han.toetsplatform.module.voorbereiden.data.SqlLoader;
import nl.han.toetsplatform.module.voorbereiden.data.VragenDao;

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


    @Inject
    public SqlVragenDao(SqlLoader _sqlLoader, StorageDao _storageDao) {
        this._sqlLoader = _sqlLoader;
        this._storageDao = _storageDao;
    }

    @Override
    public void insertVraag(VragenbankVraagDto vraag) {
        Connection conn = _storageDao.getConnection();

        try {
            PreparedStatement ps = conn.prepareStatement(_sqlLoader.load("insert_vraag"));
            ps.setString(1, vraag.getId().toString());
            ps.setString(2, vraag.getNaam());
            ps.setString(3, vraag.getVraagtype());
            ps.setString(4, vraag.getThema());
            ps.setInt(5, vraag.getPunten());
            ps.setString(6, vraag.getNakijkInstructies());
            ps.setString(7, vraag.getVraagData());
            ps.setString(8, vraag.getNakijkModel());
            ps.setInt(9, vraag.getVersie().getNummer());
            ps.setLong(10, vraag.getVersie().getDatum());
            ps.setString(11, vraag.getVersie().getOmschrijving());
            ps.execute();

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Could not insert vraag: " + e.getMessage());
        }
        _storageDao.closeConnection();
    }

    public void insertTentamenVraag(SamengesteldTentamenDto tentamen, VragenbankVraagDto vraag) {
        Connection conn = _storageDao.getConnection();

        try {
            PreparedStatement ps = conn.prepareStatement(_sqlLoader.load("insert_tentamen_vraag"));
            ps.setString(1, vraag.getId().toString());
            ps.setInt(2, vraag.getVersie().getNummer());
            ps.setString(3, tentamen.getId().toString());
            ps.setInt(4, tentamen.getVersie().getNummer());
            ps.execute();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Could not insert tentamen vraag: " + e.getMessage());
        }
    }

    public List<VragenbankVraagDto> getVragenVanTentamen(SamengesteldTentamenDto tentamen) {
        Connection conn = _storageDao.getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement(_sqlLoader.load("select_vraag_in_tentamen"));
            ps.setString(1, tentamen.getId().toString());
            ps.setInt(2, tentamen.getVersie().getNummer());

            ResultSet rs = ps.executeQuery();
            List<VragenbankVraagDto> vragen =  readVraagResulSet(rs);
            rs.close();
            return vragen;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }

    private List<VragenbankVraagDto> readVraagResulSet(ResultSet rs) throws SQLException {
        List<VragenbankVraagDto> vragen = new ArrayList<>();
        while (rs.next()) {
            VragenbankVraagDto vraag = new VragenbankVraagDto();
            vraag.setId(UUID.fromString(rs.getString("id")));
            vraag.setNaam(rs.getString("naam"));
            vraag.setThema(rs.getString("thema"));
            vraag.setVraagtype(rs.getString("vraag_type"));
            vraag.setVraagData(rs.getString("vraag_data"));
            vraag.setNakijkModel(rs.getString("nakijk_model"));
            vraag.setNakijkInstructies(rs.getString("nakijkInstrucites"));
            VersieDto versie = new VersieDto();
            versie.setNummer(rs.getInt("versie_nummer"));
            versie.setDatum(rs.getLong("versie_datum"));
            versie.setOmschrijving(rs.getString("versie_omschrijving"));
            vraag.setVersie(versie);

            vragen.add(vraag);
        }
        return vragen;
    }

    public List<VragenbankVraagDto> getVragen() {
        Connection conn = _storageDao.getConnection();
        if (conn == null) return new ArrayList<>();

        try {
            PreparedStatement ps = conn.prepareStatement(_sqlLoader.load("select_vraag"));
            ResultSet rs = ps.executeQuery();

            List<VragenbankVraagDto> vragen = readVraagResulSet(rs);
            rs.close();
            return vragen;
        } catch (SQLException e) {

        }
        return new ArrayList<>();
    }
}
