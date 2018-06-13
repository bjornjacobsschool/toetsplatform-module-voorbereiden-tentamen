package nl.han.toetsplatform.module.voorbereiden.applicationlayer;

import nl.han.toetsapplicatie.apimodels.dto.KlaargezetTentamenDto;
import nl.han.toetsapplicatie.apimodels.dto.SamengesteldTentamenDto;
import nl.han.toetsplatform.module.voorbereiden.data.TentamenDao;
import nl.han.toetsplatform.module.voorbereiden.exceptions.GatewayCommunicationException;
import nl.han.toetsplatform.module.voorbereiden.models.KlaargezetTentamen;
import nl.han.toetsplatform.module.voorbereiden.models.Tentamen;
import nl.han.toetsplatform.module.voorbereiden.models.Versie;
import nl.han.toetsplatform.module.voorbereiden.models.Vraag;
import nl.han.toetsplatform.module.voorbereiden.serviceagent.IGatewayServiceAgent;

import javax.inject.Inject;
import javax.ws.rs.GET;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
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

    public void opslaan(KlaargezetTentamenDto tentamen) throws GatewayCommunicationException, SQLException {

        _tentamenDao.setTentamenKlaar(tentamen);

        // tentamen versturen naar gateway
        // todo URL specificeren voor post request opslaan tentamen
        this._gatewayServiceAgent.post("/tentamens/klaargezet", tentamen);
        System.out.println("Gecommuniceerd met gateway");

    }

    @Override
    public List<KlaargezetTentamenDto> getKlaargezetteTentamens() {
//   return _tentamenDao.loadTentamens();

            ArrayList<KlaargezetTentamenDto> klaargezetteTentamens = new ArrayList<>();

        try {
            klaargezetteTentamens.addAll(Arrays.asList(this._gatewayServiceAgent.get("tentamens/klaargezet", KlaargezetTentamenDto[].class)));
        } catch (GatewayCommunicationException e) {
            e.printStackTrace();
        }
        return klaargezetteTentamens;
    }
}

