package nl.han.toetsplatform.module.voorbereiden.applicationlayer;

import nl.han.toetsapplicatie.apimodels.dto.SamengesteldTentamenDto;
import nl.han.toetsplatform.module.voorbereiden.data.TentamenDao;
import nl.han.toetsplatform.module.voorbereiden.data.sql.SqlTentamenDao;
import nl.han.toetsplatform.module.voorbereiden.exceptions.GatewayCommunicationException;
import nl.han.toetsplatform.module.voorbereiden.models.KlaargezetTentamen;
import nl.han.toetsplatform.module.voorbereiden.models.Tentamen;
import nl.han.toetsplatform.module.voorbereiden.models.Versie;
import nl.han.toetsplatform.module.voorbereiden.models.Vraag;
import nl.han.toetsplatform.module.voorbereiden.serviceagent.IGatewayServiceAgent;

import javax.inject.Inject;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
    public List<Tentamen> getTentamens() {
        List<Tentamen> tentamen = _tentamenDao.loadTentamens();

        Class<List<Tentamen>> clazz = (Class) List.class;
       // try {
//           List<Tentamen> serverTentamens = this._gatewayServiceAgent.get("/tentamens/samengesteld", clazz);


           /* for(Vraag sT : serverTentamens){
                boolean exists = false;
                for(Vraag lV : localVragen){
                    if(sV.getId().equals(lV.getId())){ //TODO: Check versie
                        exists = true;
                        break;
                    }
                }
                if(!exists){
                    LOGGER.log(Level.INFO, "Nieuwe vraag van server: " + sV.getId().toString());
                    _vragenDao.insertVraag(sV);
                }
            }*/

      //  } catch (GatewayCommunicationException e) {
        //    LOGGER.log(Level.WARNING, "Could not connect to gateway: " + e.getMessage());
       // }

        return tentamen;
    }
}

