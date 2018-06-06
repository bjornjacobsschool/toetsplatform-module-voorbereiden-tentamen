package nl.han.toetsplatform.module.voorbereiden.applicationlayer;

import nl.han.toetsplatform.module.voorbereiden.exceptions.GatewayCommunicationException;
import nl.han.toetsplatform.module.voorbereiden.models.KlaargezetTentamen;
import nl.han.toetsplatform.module.voorbereiden.models.Tentamen;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface ITentamenKlaarzetten {
    void opslaan(KlaargezetTentamen tentamen) throws GatewayCommunicationException, SQLException;

    List<Tentamen> getTentamens();
}
