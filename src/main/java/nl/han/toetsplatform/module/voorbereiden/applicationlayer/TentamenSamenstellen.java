package nl.han.toetsplatform.module.voorbereiden.applicationlayer;

import nl.han.toetsplatform.module.shared.storage.StorageDao;
import nl.han.toetsplatform.module.voorbereiden.data.TentamenDao;
import nl.han.toetsplatform.module.voorbereiden.exceptions.GatewayCommunicationException;
import nl.han.toetsplatform.module.voorbereiden.models.Tentamen;
import nl.han.toetsplatform.module.voorbereiden.serviceagent.IGatewayServiceAgent;

import javax.inject.Inject;
import java.sql.*;
import java.util.List;
import java.util.logging.Logger;

public class TentamenSamenstellen implements ITentamenSamenstellen {

    private final static Logger LOGGER = Logger.getLogger(TentamenSamenstellen.class.getName());

    private IGatewayServiceAgent _gatewayServiceAgent;
    private StorageDao _storageDAO;

    TentamenDao _tentamenDao;

    @Inject
    public TentamenSamenstellen(IGatewayServiceAgent gatewayServiceAgent, StorageDao storageDao, TentamenDao tentamenDao)
    {
        this._gatewayServiceAgent = gatewayServiceAgent;
        this._storageDAO = storageDao;
        this._tentamenDao = tentamenDao;
    }

    public void opslaan(Tentamen tentamen) throws GatewayCommunicationException, SQLException {
        // tentamen opslaan in lokale database
        _tentamenDao.saveTentamen(tentamen);

        // tentamen versturen naar gateway
        // todo URL specificeren voor post request opslaan tentamen
        this._gatewayServiceAgent.post("", tentamen);
        System.out.println("Gecommuniceerd met gateway");
    }

}
