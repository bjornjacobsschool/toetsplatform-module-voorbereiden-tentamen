package nl.han.toetsplatform.module.voorbereiden.applicationlayer;

import nl.han.toetsapplicatie.apimodels.dto.KlaargezetTentamenDto;
import nl.han.toetsapplicatie.apimodels.dto.SamengesteldTentamenDto;
import nl.han.toetsplatform.module.voorbereiden.data.TentamenDao;
import nl.han.toetsplatform.module.voorbereiden.exceptions.GatewayCommunicationException;
import nl.han.toetsplatform.module.voorbereiden.serviceagent.IGatewayServiceAgent;

import javax.inject.Inject;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
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

        this._gatewayServiceAgent.post("/tentamens/klaargezet", tentamen);
        _tentamenDao.setTentamenKlaar(tentamen);
    }

    @Override
    public List<SamengesteldTentamenDto> getTentamens() {
        List<SamengesteldTentamenDto> tentamen = _tentamenDao.loadTentamens();

        try {
            List<SamengesteldTentamenDto> serverTentamens = Arrays.asList(this._gatewayServiceAgent.get("/tentamens/samengesteld", SamengesteldTentamenDto[].class));

            for (SamengesteldTentamenDto serverT : serverTentamens) {
                boolean exists = false;
                for (SamengesteldTentamenDto localT : tentamen) {
                    if (serverT.getId().equals(localT.getId()) && serverT.getVersie().getNummer() == localT.getVersie().getNummer()) {
                        exists = true;
                        break;
                    }
                }
                if (!exists) {
                    LOGGER.log(Level.INFO, "Nieuwe tentamen van server: " + serverT.getId().toString());
                    _tentamenDao.saveTentamen(serverT);
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

    public String getSleutel(KlaargezetTentamenDto klaargezetTentamen){

        return "SLEUTEL";
    }
}

