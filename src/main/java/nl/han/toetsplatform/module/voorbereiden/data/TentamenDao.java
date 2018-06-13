package nl.han.toetsplatform.module.voorbereiden.data;

import nl.han.toetsapplicatie.apimodels.dto.KlaargezetTentamenDto;
import nl.han.toetsplatform.module.voorbereiden.models.KlaargezetTentamen;
import nl.han.toetsplatform.module.voorbereiden.models.Tentamen;

import java.util.List;

public interface TentamenDao {

    void saveTentamen(Tentamen tentamen);

    List<Tentamen> loadTentamens();

    void setTentamenKlaar(KlaargezetTentamenDto tentamen);
}
