package nl.han.toetsplatform.module.voorbereiden.applicationlayer;

import nl.han.toetsplatform.module.shared.storage.StorageDao;
import nl.han.toetsplatform.module.voorbereiden.exceptions.GatewayCommunicationException;
import nl.han.toetsplatform.module.voorbereiden.models.Tentamen;
import nl.han.toetsplatform.module.voorbereiden.models.Versie;
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

        // tentamen versturen naar gateway
        // todo URL specificeren voor post request opslaan tentamen
        this._gatewayServiceAgent.post("", tentamen);
        System.out.println("Gecommuniceerd met gateway");

        // tentamen opslaan in lokale database
        // todo script voor opslaan van tentamen invoegen
        _storageDAO.executeQuery("");
        System.out.println("Gecommuniceerd met database");
    }

    @Override
    public ArrayList<Tentamen> getTentamens() {
        ArrayList<Tentamen> list = new ArrayList<>();
        Tentamen tentamen1 = new Tentamen();
        tentamen1.setVak("APP");
        tentamen1.setNaam("App Algorithmes 1");
        tentamen1.setBeschrijving("Beschrijving veld");
        tentamen1.setToegestaandeHulpmiddelen("Hulpmiddeleen");
        tentamen1.setTijdsduur(90);
        tentamen1.setVersie(new Versie());

        Tentamen tentamen = new Tentamen();
        tentamen.setVak("SWA");
        tentamen.setNaam("SWA 1");
        tentamen.setBeschrijving("Beschrijving veld");
        tentamen.setToegestaandeHulpmiddelen("Hulpmiddeleen");
        tentamen.setTijdsduur(90);
        tentamen.setVersie(new Versie());

        list.add(tentamen1);
        list.add(tentamen);
        return list;
    }
}
