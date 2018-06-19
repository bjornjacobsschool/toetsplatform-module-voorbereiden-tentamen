package nl.han.toetsplatform.module.voorbereiden.applicationlayer;

import nl.han.toetsapplicatie.apimodels.dto.KlaargezetTentamenDto;
import nl.han.toetsapplicatie.apimodels.dto.SamengesteldTentamenDto;
import nl.han.toetsplatform.module.voorbereiden.exceptions.GatewayCommunicationException;

import java.sql.SQLException;
import java.util.List;

public interface ITentamenKlaarzetten {
    void opslaan(KlaargezetTentamenDto tentamen) throws GatewayCommunicationException, SQLException;

    List<SamengesteldTentamenDto> getTentamens();
    List<KlaargezetTentamenDto> getKlaargezetteTentamens();
    String getSleutel(KlaargezetTentamenDto klaargezetTentamen);
}
