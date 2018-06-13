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
import java.util.UUID;
import java.util.logging.Level;
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

    public void opslaan(SamengesteldTentamenDto tentamen) throws GatewayCommunicationException, SQLException {
        // tentamen opslaan in lokale database
        _tentamenDao.saveTentamen(tentamen);

        // tentamen versturen naar gateway
        // todo URL specificeren voor post request opslaan tentamen
        this._gatewayServiceAgent.post("tentamens/samengesteld", tentamen);
        System.out.println("Gecommuniceerd met gateway");
    }

    @Override
    public void slaVraagOp(VragenbankVraagDto vraag) {
        _vragenDao.insertVraag(vraag);

        try {
            this._gatewayServiceAgent.post("vragenbank", (VragenbankVraagDto)vraag);
        } catch (GatewayCommunicationException e) {
            e.printStackTrace();
        }

    }

    public List<VragenbankVraagDto> getVragen(){
        List<VragenbankVraagDto> localVragen = _vragenDao.getVragen();


       try {
            List<VragenbankVraagDto> serverVragen =  Arrays.asList(this._gatewayServiceAgent.get("vragenbank", VragenbankVraagDto[].class));

            for(VragenbankVraagDto sV : serverVragen){
                String u = sV.getId().toString();
                UUID u2 =  UUID.fromString(u);

                boolean exists = false;
                for(VragenbankVraagDto lV : localVragen){
                    System.out.println(sV.getId().toString() + " - " + lV.getId().toString());
                    if(sV.getId().toString().equals(lV.getId().toString())){
                        exists = true;
                        break;
                    }
                }
                if(!exists){
                    LOGGER.log(Level.INFO, "Nieuwe vraag van server: " + sV.getId().toString());
                    _vragenDao.insertVraag(sV);
                }
            }

        } catch (GatewayCommunicationException e) {
            LOGGER.log(Level.WARNING, "Could not connect to gatewary: " + e.getMessage());
        }

        return _vragenDao.getVragen();
    }

}
