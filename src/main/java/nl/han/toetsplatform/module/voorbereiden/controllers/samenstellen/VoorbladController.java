package nl.han.toetsplatform.module.voorbereiden.controllers.samenstellen;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import nl.han.toetsapplicatie.apimodels.dto.SamengesteldTentamenDto;
import nl.han.toetsapplicatie.apimodels.dto.VersieDto;

import java.util.UUID;
import java.util.function.Consumer;

import static nl.han.toetsplatform.module.voorbereiden.util.RunnableUtil.runIfNotNull;

public class VoorbladController {

    public TextField naamField;
    public TextField vakField;
    public TextArea beschrijvingArea;
    public TextArea toegestaandeHulpmiddelenArea;
    private Consumer<SamengesteldTentamenDto> onVoorbladAanmaken;
    private Runnable onGeannuleerd;

    /**
     * Setter
     * @param onVoorbladAanmaken
     */
    public void setOnVoorbladAanmaken(Consumer<SamengesteldTentamenDto> onVoorbladAanmaken) { this.onVoorbladAanmaken = onVoorbladAanmaken; }

    @FXML
    protected void handleAnnulerenButtonAction(ActionEvent event)
    {
        runIfNotNull(onGeannuleerd);
    }

    public void setOnGeannuleerd(Runnable onGeannuleerd) {
        this.onGeannuleerd = onGeannuleerd;
    }

    @FXML
    protected void handleVoorbladAanmakenButtonAction(ActionEvent event)
    {
        SamengesteldTentamenDto tentamen = new SamengesteldTentamenDto();
        tentamen.setId(UUID.randomUUID());
        VersieDto versie = new VersieDto();
        versie.setNummer(1);
        versie.setDatum(System.currentTimeMillis());
        versie.setOmschrijving("Eerste versie.");
        tentamen.setVersie(versie);
        tentamen.setTijdsduur("2 uur");
        tentamen.setNaam(naamField.getText());
        tentamen.setBeschrijving(beschrijvingArea.getText());
        tentamen.setVak(vakField.getText());
        tentamen.setToegestaandeHulpmiddelen(toegestaandeHulpmiddelenArea.getText());

        runIfNotNull(onVoorbladAanmaken, tentamen);
    }
}
