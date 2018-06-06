package nl.han.toetsplatform.module.voorbereiden.applicationlayer;

import nl.han.toetsplatform.module.voorbereiden.data.TentamenDao;
import nl.han.toetsplatform.module.voorbereiden.exceptions.GatewayCommunicationException;
import nl.han.toetsplatform.module.voorbereiden.models.KlaargezetTentamen;
import nl.han.toetsplatform.module.voorbereiden.models.Tentamen;
import nl.han.toetsplatform.module.voorbereiden.serviceagent.IGatewayServiceAgent;

import javax.inject.Inject;
import java.sql.SQLException;
import java.util.List;

public class TentamenKlaarzetten implements ITentamenKlaarzetten{
    private IGatewayServiceAgent _gatewayServiceAgent;
    private TentamenDao _tentamenDao;

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
        this._gatewayServiceAgent.post("", tentamen);
        System.out.println("Gecommuniceerd met gateway");

    }

    @Override
    public List<Tentamen> getTentamens() {
        return _tentamenDao.loadTentamens();
    }
}

