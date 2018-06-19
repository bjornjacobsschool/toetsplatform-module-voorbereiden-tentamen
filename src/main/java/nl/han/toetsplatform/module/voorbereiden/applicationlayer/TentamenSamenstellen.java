package nl.han.toetsplatform.module.voorbereiden.applicationlayer;

import nl.han.toetsapplicatie.apimodels.dto.SamengesteldTentamenDto;
import nl.han.toetsapplicatie.apimodels.dto.VragenbankVraagDto;
import nl.han.toetsplatform.module.shared.storage.StorageDao;
import nl.han.toetsplatform.module.voorbereiden.data.TentamenDao;
import nl.han.toetsplatform.module.voorbereiden.data.VragenDao;
import nl.han.toetsplatform.module.voorbereiden.exceptions.GatewayCommunicationException;
import nl.han.toetsplatform.module.voorbereiden.serviceagent.IGatewayServiceAgent;

import javax.inject.Inject;

import java.sql.*;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TentamenSamenstellen implements ITentamenSamenstellen {

    private final static Logger LOGGER = Logger.getLogger(TentamenSamenstellen.class.getName());

    private IGatewayServiceAgent _gatewayServiceAgent;
    private StorageDao _storageDAO;

    private TentamenDao _tentamenDao;

    private VragenDao _vragenDao;

    @Inject
    public TentamenSamenstellen(IGatewayServiceAgent gatewayServiceAgent, StorageDao storageDao, TentamenDao tentamenDao, VragenDao vragenDao) {
        this._gatewayServiceAgent = gatewayServiceAgent;
        this._storageDAO = storageDao;
        this._tentamenDao = tentamenDao;
        this._vragenDao = vragenDao;
    }

    public void slaTentamenOp(SamengesteldTentamenDto tentamen) throws GatewayCommunicationException, SQLException {
        _tentamenDao.saveTentamen(tentamen);
        this._gatewayServiceAgent.post("tentamens/samengesteld", tentamen);
    }

    @Override
    public void slaVraagOp(VragenbankVraagDto vraag) {
        _vragenDao.insertVraag(vraag);
        try {
            this._gatewayServiceAgent.post("vragenbank", (VragenbankVraagDto) vraag);
        } catch (GatewayCommunicationException e) {
            LOGGER.log(Level.WARNING, "Could not save vraag to gateway: " + e.getMessage());
        }
    }

    public List<VragenbankVraagDto> getVragen() {
        List<VragenbankVraagDto> localVragen = _vragenDao.getVragen();
        try {
            List<VragenbankVraagDto> serverVragen = Arrays.asList(this._gatewayServiceAgent.get("vragenbank", VragenbankVraagDto[].class));

            for (VragenbankVraagDto serverV : serverVragen) {
                boolean exists = false;
                for (VragenbankVraagDto localV : localVragen) {
                    if (serverV.getId().equals(localV.getId()) && serverV.getVersie().getNummer() == localV.getVersie().getNummer()) {
                        exists = true;
                        break;
                    }
                }
                if (!exists) {
                    LOGGER.log(Level.INFO, "Nieuwe vraag van server: " + serverV.getId().toString());
                    _vragenDao.insertVraag(serverV);
                }
            }
        } catch (GatewayCommunicationException e) {
            LOGGER.log(Level.WARNING, "Could not connect to gatewary: " + e.getMessage());
        }
        return _vragenDao.getVragen();
    }
}
