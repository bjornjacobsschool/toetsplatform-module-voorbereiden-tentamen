package nl.han.toetsplatform.module.voorbereiden.applicationlayer;

import nl.han.toetsplatform.module.shared.storage.StorageDao;
import nl.han.toetsplatform.module.voorbereiden.exceptions.GatewayCommunicationException;
import nl.han.toetsplatform.module.voorbereiden.models.Tentamen;
import nl.han.toetsplatform.module.voorbereiden.serviceagent.IGatewayServiceAgent;

import javax.inject.Inject;
import java.sql.SQLException;
import java.util.ArrayList;

public class TentamenKlaarzetten implements ITentamenKlaarzetten{
    private IGatewayServiceAgent _gatewayServiceAgent;
    private StorageDao _storageDAO;

    @Inject
    public TentamenKlaarzetten(IGatewayServiceAgent gatewayServiceAgent, StorageDao storageDao)
    {
        this._gatewayServiceAgent = gatewayServiceAgent;
        this._storageDAO = storageDao;
    }

    public void opslaan(Tentamen tentamen) throws GatewayCommunicationException, SQLException {

//        // tentamen versturen naar gateway
//        // todo URL specificeren voor post request opslaan tentamen
//        this._gatewayServiceAgent.post("", tentamen);
//        System.out.println("Gecommuniceerd met gateway");
//
//        // tentamen opslaan in lokale database
//        // todo script voor opslaan van tentamen invoegen
//        _storageDAO.executeQuery("");
//        System.out.println("Gecommuniceerd met database");
    }

    @Override
    public ArrayList<Tentamen> getTentamens() {
        ArrayList<Tentamen> list = new ArrayList<>();
        Tentamen tentamen = new Tentamen();
        tentamen.setVak("Vak veld");
        tentamen.setNaam("Naam veld");
        tentamen.setBeschrijving("Beschrijving veld");
        list.add(tentamen);
        return list;
    }
}
