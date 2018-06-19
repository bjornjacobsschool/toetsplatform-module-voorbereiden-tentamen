package nl.han.toetsplatform.module.voorbereiden.data;

import nl.han.toetsapplicatie.apimodels.dto.KlaargezetTentamenDto;
import nl.han.toetsapplicatie.apimodels.dto.SamengesteldTentamenDto;

import java.util.List;

public interface TentamenDao {

    void saveTentamen(SamengesteldTentamenDto tentamen);

    List<SamengesteldTentamenDto> loadTentamens();

    void setTentamenKlaar(KlaargezetTentamenDto tentamen);
}
