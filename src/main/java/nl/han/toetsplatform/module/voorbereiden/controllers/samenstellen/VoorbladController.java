package nl.han.toetsplatform.module.voorbereiden.controllers.samenstellen;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
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

    /**
     * Setter
     * @param onVoorbladAanmaken
     */
    public void setOnVoorbladAanmaken(Consumer<Tentamen> onVoorbladAanmaken) { this.onVoorbladAanmaken = onVoorbladAanmaken; }

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
        Tentamen tentamen = new Tentamen();
        tentamen.setNaam(naamField.getText());
        tentamen.setBeschrijving(beschrijvingArea.getText());
        tentamen.setVak(vakField.getText());
        tentamen.setToegestaandeHulpmiddelen(toegestaandeHulpmiddelenArea.getText());

        runIfNotNull(onVoorbladAanmaken, tentamen);
    }
}
