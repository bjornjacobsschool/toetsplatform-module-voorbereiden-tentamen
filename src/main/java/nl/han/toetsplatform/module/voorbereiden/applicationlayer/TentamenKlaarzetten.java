package nl.han.toetsplatform.module.voorbereiden.applicationlayer;

import nl.han.toetsapplicatie.apimodels.dto.SamengesteldTentamenDto;
import nl.han.toetsplatform.module.voorbereiden.data.TentamenDao;
import nl.han.toetsplatform.module.voorbereiden.exceptions.GatewayCommunicationException;
import nl.han.toetsplatform.module.voorbereiden.models.KlaargezetTentamen;
import nl.han.toetsplatform.module.voorbereiden.serviceagent.IGatewayServiceAgent;

import javax.inject.Inject;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TentamenKlaarzetten implements ITentamenKlaarzetten{
    private IGatewayServiceAgent _gatewayServiceAgent;
    private TentamenDao _tentamenDao;

    private final static Logger LOGGER = Logger.getLogger(TentamenKlaarzetten.class.getName());

    @Inject
    public TentamenKlaarzetten(IGatewayServiceAgent gatewayServiceAgent, TentamenDao tentamenDao)
    {
        this._gatewayServiceAgent = gatewayServiceAgent;
        this._tentamenDao = tentamenDao;
    }

    public void opslaan(KlaargezetTentamen tentamen) throws GatewayCommunicationException, SQLException {

        _tentamenDao.setTentamenKlaar(tentamen);

        // tentamen versturen naar gateway
        // todo URL specificeren voor post request opslaan tentamen
        this._gatewayServiceAgent.post("/tentamens/samengesteld", tentamen);

        System.out.println("Gecommuniceerd met gateway");

    }

    @Override
    public List<SamengesteldTentamenDto> getTentamens() {
        List<SamengesteldTentamenDto> tentamen = _tentamenDao.loadTentamens();

        try {
           List<SamengesteldTentamenDto> serverTentamens = Arrays.asList(this._gatewayServiceAgent.get("/tentamens/samengesteld", SamengesteldTentamenDto[].class));

            for(SamengesteldTentamenDto sT : serverTentamens){
                boolean exists = false;
                for(SamengesteldTentamenDto lT : tentamen){
                    if(sT.getId().equals(lT.getId())){ //TODO: Check versie
                        exists = true;
                        break;
                    }
                }
                if(!exists){
                    LOGGER.log(Level.INFO, "Nieuwe tentamen van server: " + sT.getId().toString());
                    _tentamenDao.saveTentamen(sT);
                }
            }

        } catch (GatewayCommunicationException e) {
            LOGGER.log(Level.WARNING, "Could not connect to gateway: " + e.getMessage());
        }

        return _tentamenDao.loadTentamens();
    }
}

