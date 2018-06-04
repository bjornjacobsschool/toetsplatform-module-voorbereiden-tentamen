package nl.han.toetsplatform.module.voorbereiden.applicationlayer;

import nl.han.toetsplatform.module.shared.storage.StorageDao;
import nl.han.toetsplatform.module.voorbereiden.data.TentamenDao;
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


        // tentamen opslaan in lokale database
        // todo script voor opslaan van tentamen invoegen
        //._storageDAO.executeQuery("");
        System.out.println("Gecommuniceerd met database");
        _tentamenDao.setTentamenKlaar(tentamen);

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
    public List<Tentamen> getTentamens() {
        return _tentamenDao.loadTentamens();
    public ArrayList<Tentamen> getTentamens() {
        ArrayList<Tentamen> list = new ArrayList<>();
        Tentamen tentamen1 = new Tentamen();
        tentamen1.setNaam("App Algorithmes 1");
        tentamen1.setBeschrijving("Beschrijving veld");
        tentamen1.setToegestaandeHulpmiddelen("Hulpmiddeleen");
        tentamen1.setVak("APP");
        tentamen1.setTijdsduur(90);
        Versie versie = new Versie();

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        versie.setDatum(dateFormat.format(date));
        versie.setNumber("1.0");
        versie.setOmschrijving("Eerste versie...");
        tentamen1.setVersie(versie);

        tentamen1.setVerzegeld(false);
        tentamen1.setHash("Hashh");
        List<Vraag> vragen = new ArrayList<>();
        Vraag vraag1 = new Vraag();
        vraag1.setNaam("Bomenvraag");
        vraag1.setVraagType("Graph");
        vraag1.setThema("Grahp Thema");
        vraag1.setVersie(versie);
        vraag1.setPunten(5);
        vraag1.setNakijkInstrucites("Nakijkinstructies voor het nakijken van de vraag");
        vraag1.setNakijkModel("Nakijkmodel van de vraag");
        vraag1.setVraagData("Data voor de vraag");
        vragen.add(vraag1);

        Vraag vraag2 = new Vraag();
        vraag2.setNaam("Bomenvraag");
        vraag2.setVraagType("Graph");
        vraag2.setThema("Grahp Thema");
        vraag2.setVersie(versie);
        vraag2.setPunten(5);
        vraag2.setNakijkInstrucites("Nakijkinstructies voor het nakijken van de vraag");
        vraag2.setNakijkModel("Nakijkmodel van de vraag");
        vraag2.setVraagData("Data voor de vraag");
        vragen.add(vraag2);

        tentamen1.setVragen(vragen);

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
