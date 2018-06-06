package nl.han.toetsplatform.module.voorbereiden.data;

import nl.han.toetsplatform.module.voorbereiden.models.Tentamen;
import nl.han.toetsplatform.module.voorbereiden.models.Vraag;

public interface VragenDao {

    void insertVraag(Vraag vraag);

    void insertTentamenVraag(Tentamen tentamen, Vraag vraag);
}
