package nl.han.toetsplatform.module.voorbereiden.applicationlayer;

import nl.han.toetsapplicatie.apimodels.dto.KlaargezetTentamenDto;
import nl.han.toetsapplicatie.apimodels.dto.SamengesteldTentamenDto;
import nl.han.toetsplatform.module.shared.storage.StorageDao;
import nl.han.toetsplatform.module.voorbereiden.data.TentamenDao;
import nl.han.toetsplatform.module.voorbereiden.exceptions.GatewayCommunicationException;
import nl.han.toetsplatform.module.voorbereiden.models.Tentamen;
import nl.han.toetsplatform.module.voorbereiden.serviceagent.IGatewayServiceAgent;

import javax.inject.Inject;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

public class TentamenSamenstellen implements ITentamenSamenstellen {

    private final static Logger LOGGER = Logger.getLogger(TentamenSamenstellen.class.getName());

    private IGatewayServiceAgent _gatewayServiceAgent;
    private StorageDao _storageDAO;

    private TentamenDao _tentamenDao;

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

    @Override
    public List<SamengesteldTentamenDto> getSamengesteldeTentamens(){

        ArrayList<SamengesteldTentamenDto> samengesteldTentamens = new ArrayList<>();
        try {
        samengesteldTentamens.addAll(Arrays.asList(this._gatewayServiceAgent.get("tentamens/samengesteld", SamengesteldTentamenDto[].class)));
        } catch (GatewayCommunicationException e) {
            e.printStackTrace();
        }
        return samengesteldTentamens;

    }

}
