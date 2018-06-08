package nl.han.toetsplatform.module.voorbereiden.applicationlayer;

import nl.han.toetsplatform.module.voorbereiden.exceptions.GatewayCommunicationException;
import nl.han.toetsplatform.module.voorbereiden.models.Tentamen;
import nl.han.toetsplatform.module.voorbereiden.models.Vraag;

import java.sql.SQLException;
import java.util.List;

public interface ITentamenSamenstellen {
    void opslaan(Tentamen tentamen) throws GatewayCommunicationException, SQLException;

    void slaVraagOp(Vraag vraag);

    List<Vraag> getVragen();
}
