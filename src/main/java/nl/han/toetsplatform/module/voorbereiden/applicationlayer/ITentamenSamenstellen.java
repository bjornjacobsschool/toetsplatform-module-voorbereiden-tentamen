package nl.han.toetsplatform.module.voorbereiden.applicationlayer;

import nl.han.toetsplatform.module.voorbereiden.exceptions.GatewayCommunicationException;
import nl.han.toetsplatform.module.voorbereiden.models.Tentamen;

import java.sql.SQLException;

public interface ITentamenSamenstellen {
    void opslaan(Tentamen tentamen) throws GatewayCommunicationException, SQLException;
}
