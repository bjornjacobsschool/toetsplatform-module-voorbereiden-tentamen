package nl.han.toetsplatform.module.voorbereiden.applicationlayer;

import nl.han.toetsplatform.module.shared.storage.StorageDao;
import nl.han.toetsplatform.module.voorbereiden.data.TentamenDao;
import nl.han.toetsplatform.module.voorbereiden.data.VragenDao;
import nl.han.toetsplatform.module.voorbereiden.exceptions.GatewayCommunicationException;
import nl.han.toetsplatform.module.voorbereiden.models.Tentamen;
import nl.han.toetsplatform.module.voorbereiden.models.Vraag;
import nl.han.toetsplatform.module.voorbereiden.serviceagent.IGatewayServiceAgent;

import javax.inject.Inject;
import java.sql.*;
import java.util.List;
import java.util.logging.Logger;

public class TentamenSamenstellen implements ITentamenSamenstellen {

    private final static Logger LOGGER = Logger.getLogger(TentamenSamenstellen.class.getName());

    private IGatewayServiceAgent _gatewayServiceAgent;
    private StorageDao _storageDAO;

    private TentamenDao _tentamenDao;

    private VragenDao _vragenDao;

    @Inject
    public TentamenSamenstellen(IGatewayServiceAgent gatewayServiceAgent, StorageDao storageDao, TentamenDao tentamenDao, VragenDao vragenDao)
    {
        this._gatewayServiceAgent = gatewayServiceAgent;
        this._storageDAO = storageDao;
        this._tentamenDao = tentamenDao;
        this._vragenDao = vragenDao;
    }

    public void opslaan(Tentamen tentamen) throws GatewayCommunicationException, SQLException {
        // tentamen opslaan in lokale database
        _tentamenDao.saveTentamen(tentamen);

        // tentamen versturen naar gateway
        // todo URL specificeren voor post request opslaan tentamen
        this._gatewayServiceAgent.post("/tentamens/samengesteld", tentamen);
        System.out.println("Gecommuniceerd met gateway");
    }

    @Override
    public void slaVraagOp(Vraag vraag) {
        _vragenDao.insertVraag(vraag);
    }

    public List<Vraag> getVragen(){
        return _vragenDao.getVragen();
    }

}
