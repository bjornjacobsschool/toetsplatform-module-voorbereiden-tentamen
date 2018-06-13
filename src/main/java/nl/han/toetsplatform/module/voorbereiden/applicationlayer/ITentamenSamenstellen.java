package nl.han.toetsplatform.module.voorbereiden.applicationlayer;

import nl.han.toetsapplicatie.apimodels.dto.SamengesteldTentamenDto;
import nl.han.toetsapplicatie.apimodels.dto.VragenbankVraagDto;
import nl.han.toetsapplicatie.apimodels.dto.SamengesteldTentamenDto;
import nl.han.toetsplatform.module.voorbereiden.exceptions.GatewayCommunicationException;

import java.sql.SQLException;
import java.util.List;

public interface ITentamenSamenstellen {
    List<SamengesteldTentamenDto> getSamengesteldeTentamens();
    void opslaan(SamengesteldTentamenDto tentamen) throws GatewayCommunicationException, SQLException;

    void slaVraagOp(VragenbankVraagDto vraag);

    List<VragenbankVraagDto> getVragen();
}
