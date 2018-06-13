package nl.han.toetsplatform.module.voorbereiden.applicationlayer;

import nl.han.toetsapplicatie.apimodels.dto.SamengesteldTentamenDto;
import nl.han.toetsplatform.module.voorbereiden.exceptions.GatewayCommunicationException;
import nl.han.toetsplatform.module.voorbereiden.models.Tentamen;

import java.sql.SQLException;
import java.util.List;

public interface ITentamenSamenstellen {
    void opslaan(Tentamen tentamen) throws GatewayCommunicationException, SQLException;

    List<SamengesteldTentamenDto> getSamengesteldeTentamens();
}
