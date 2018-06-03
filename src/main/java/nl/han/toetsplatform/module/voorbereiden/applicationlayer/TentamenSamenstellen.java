package nl.han.toetsplatform.module.voorbereiden.applicationlayer;

import nl.han.toetsplatform.module.shared.storage.StorageDao;
import nl.han.toetsplatform.module.voorbereiden.data.SqlLoader;
import nl.han.toetsplatform.module.voorbereiden.exceptions.GatewayCommunicationException;
import nl.han.toetsplatform.module.voorbereiden.models.Tentamen;
import nl.han.toetsplatform.module.voorbereiden.serviceagent.GatewayServiceAgent;
import nl.han.toetsplatform.module.voorbereiden.serviceagent.IGatewayServiceAgent;

import javax.inject.Inject;
import java.sql.*;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TentamenSamenstellen implements ITentamenSamenstellen {

    private final static Logger LOGGER = Logger.getLogger(SqlLoader.class.getName());

    private IGatewayServiceAgent _gatewayServiceAgent;
    private StorageDao _storageDAO;

    @Inject
    SqlLoader sqlLoader;

    @Inject
    StorageDao storageDao;

    @Inject
    public TentamenSamenstellen(IGatewayServiceAgent gatewayServiceAgent, StorageDao storageDao)
    {
        this._gatewayServiceAgent = gatewayServiceAgent;
        this._storageDAO = storageDao;
    }

    public void opslaan(Tentamen tentamen) throws GatewayCommunicationException, SQLException {
        // tentamen opslaan in lokale database
        savetoDatabase(tentamen);
        System.out.println("Gecommuniceerd met database");

        // tentamen versturen naar gateway
        // todo URL specificeren voor post request opslaan tentamen
        this._gatewayServiceAgent.post("", tentamen);
        System.out.println("Gecommuniceerd met gateway");
    }

    public void savetoDatabase(Tentamen tentamen) throws SQLException {
        Connection conn = storageDao.getConnection();
        if(conn == null){
            LOGGER.log(Level.WARNING, "No database connected");
            return;
        }

        PreparedStatement psVersie = conn.prepareStatement(sqlLoader.load("insert_versie"));
        psVersie.setDate(1, new Date(10000));
        psVersie.setString(2, "");
        psVersie.setString(3, "");
        psVersie.execute();

        Statement statement = conn.createStatement();
        ResultSet rsVersieId = statement.executeQuery("SELECT last_insert_rowid()");
        int versie_id = -1;
        while (rsVersieId.next()){
            versie_id = rsVersieId.getInt(1);
        }

        PreparedStatement psTentamen = conn.prepareStatement(sqlLoader.load("insert_tentamen"));
        psTentamen.setString(1, UUID.randomUUID().toString());
        psTentamen.setString(2, tentamen.getNaam());
        psTentamen.setString(3, tentamen.getBeschrijving());
        psTentamen.setString(4, tentamen.getToegestaandeHulpmiddelen());
        psTentamen.setString(5, tentamen.getVak());
        psTentamen.setInt(6, versie_id);
        psTentamen.execute();
        storageDao.closeConnection();

    }
}
