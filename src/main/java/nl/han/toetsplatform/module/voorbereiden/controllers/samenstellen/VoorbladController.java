package nl.han.toetsplatform.module.voorbereiden.controllers.samenstellen;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import nl.han.toetsplatform.module.voorbereiden.models.Tentamen;

import java.util.function.Consumer;

import static nl.han.toetsplatform.module.voorbereiden.util.RunnableUtil.runIfNotNull;

public class VoorbladController {

    public TextField naamField;
    public TextField vakField;
    public TextArea beschrijvingArea;
    public TextArea toegestaandeHulpmiddelenArea;
    private Consumer<Tentamen> onVoorbladAanmaken;
    private Runnable onGeannuleerd;

    public void setOnVoorbladAanmaken(Consumer<Tentamen> onVoorbladAanmaken) {
        this.onVoorbladAanmaken = onVoorbladAanmaken;
    }

    @FXML
    protected void handleAnnulerenButtonAction(ActionEvent event)
    {
        runIfNotNull(onGeannuleerd);

        System.out.println("Annuleren");
        // actie voor annuleren
    }

    public void setOnGeannuleerd(Runnable onGeannuleerd) {
        this.onGeannuleerd = onGeannuleerd;
    }

    @FXML
    protected void handleVoorbladAanmakenButtonAction(ActionEvent event)
    {
        System.out.println("Voorblad aanmaken");
        Tentamen tentamen = new Tentamen();
        tentamen.setNaam(naamField.getText());
        tentamen.setBeschrijving(beschrijvingArea.getText());
        tentamen.setVak(vakField.getText());
        tentamen.setToegestaandeHulpmiddelen(toegestaandeHulpmiddelenArea.getText());

        runIfNotNull(onVoorbladAanmaken, tentamen);
    }
}