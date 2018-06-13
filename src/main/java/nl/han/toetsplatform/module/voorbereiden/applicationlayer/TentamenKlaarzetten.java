package nl.han.toetsplatform.module.voorbereiden.applicationlayer;

import nl.han.toetsapplicatie.apimodels.dto.SamengesteldTentamenDto;
import nl.han.toetsapplicatie.apimodels.dto.KlaargezetTentamenDto;
import nl.han.toetsapplicatie.apimodels.dto.SamengesteldTentamenDto;
import nl.han.toetsplatform.module.voorbereiden.data.TentamenDao;
import nl.han.toetsplatform.module.voorbereiden.exceptions.GatewayCommunicationException;
import nl.han.toetsplatform.module.voorbereiden.models.KlaargezetTentamen;
import nl.han.toetsplatform.module.voorbereiden.serviceagent.IGatewayServiceAgent;

import javax.inject.Inject;
import javax.ws.rs.GET;
import java.sql.SQLException;
import java.util.Arrays;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TentamenKlaarzetten implements ITentamenKlaarzetten {
    private IGatewayServiceAgent _gatewayServiceAgent;
    private TentamenDao _tentamenDao;

    private final static Logger LOGGER = Logger.getLogger(TentamenKlaarzetten.class.getName());

    @Inject
    public TentamenKlaarzetten(IGatewayServiceAgent gatewayServiceAgent, TentamenDao tentamenDao) {
        this._gatewayServiceAgent = gatewayServiceAgent;
        this._tentamenDao = tentamenDao;
    }

    public void opslaan(KlaargezetTentamenDto tentamen) throws GatewayCommunicationException, SQLException {
                _tentamenDao.setTentamenKlaar(tentamen);
        this._gatewayServiceAgent.post("/tentamens/samengesteld", tentamen);
    }

    @Override
    public List<SamengesteldTentamenDto> getTentamens() {
        List<SamengesteldTentamenDto> tentamen = _tentamenDao.loadTentamens();

        try {
            List<SamengesteldTentamenDto> serverTentamens = Arrays.asList(this._gatewayServiceAgent.get("/tentamens/samengesteld", SamengesteldTentamenDto[].class));

            for (SamengesteldTentamenDto sT : serverTentamens) {
                boolean exists = false;
                for (SamengesteldTentamenDto lT : tentamen) {
                    if (sT.getId().equals(lT.getId())) { //TODO: Check versie
                        exists = true;
                        break;
                    }
                }
                if (!exists) {
                    LOGGER.log(Level.INFO, "Nieuwe tentamen van server: " + sT.getId().toString());
                    _tentamenDao.saveTentamen(sT);
                }
            }

        } catch (GatewayCommunicationException e) {
            LOGGER.log(Level.WARNING, "Could not connect to gateway: " + e.getMessage());
        }

        return _tentamenDao.loadTentamens();
    }

    public List<KlaargezetTentamenDto> getKlaargezetteTentamens() {

        ArrayList<KlaargezetTentamenDto> klaargezetteTentamens = new ArrayList<>();

        try {
            klaargezetteTentamens.addAll(Arrays.asList(this._gatewayServiceAgent.get("tentamens/klaargezet", KlaargezetTentamenDto[].class)));
        } catch (GatewayCommunicationException e) {
            e.printStackTrace();
        }
        return klaargezetteTentamens;
    }
}

