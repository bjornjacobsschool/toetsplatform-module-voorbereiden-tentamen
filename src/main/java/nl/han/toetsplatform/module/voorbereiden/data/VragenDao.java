package nl.han.toetsplatform.module.voorbereiden.data;

import nl.han.toetsapplicatie.apimodels.dto.SamengesteldTentamenDto;
import nl.han.toetsapplicatie.apimodels.dto.VragenbankVraagDto;

import java.util.List;

public interface VragenDao {

    void insertVraag(VragenbankVraagDto vraag);

    void insertTentamenVraag(SamengesteldTentamenDto tentamen, VragenbankVraagDto vraag);

    List<VragenbankVraagDto> getVragen();

    List<VragenbankVraagDto> getVragenVanTentamen(SamengesteldTentamenDto tentamen);
}
