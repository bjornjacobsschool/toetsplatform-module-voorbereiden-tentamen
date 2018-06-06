package nl.han.toetsplatform.module.voorbereiden.controllers.klaarzetten;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import nl.han.toetsplatform.module.voorbereiden.config.PrimaryStageConfig;
import nl.han.toetsplatform.module.voorbereiden.models.KlaargezetTentamen;
import nl.han.toetsplatform.module.voorbereiden.models.Tentamen;
import nl.han.toetsplatform.module.voorbereiden.util.DateTimePicker;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.function.Consumer;

import static nl.han.toetsplatform.module.voorbereiden.util.RunnableUtil.runIfNotNull;

public class KlaarzettenController {

    @FXML
    private Label tentamenVeld;

    @FXML
    private DateTimePicker vanDateVeld;

    @FXML
    private DateTimePicker totDateVeld;

    @FXML
    private TextField sleutelVeld;

    private Tentamen tentamen;
    private Stage dialogStage;
    private boolean okClicked = false;

    private Consumer<KlaargezetTentamen> onKlaarzettenTentamen;

    public void initialize(){
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setTentamen(Tentamen tentamen) {
        this.tentamen = tentamen;
        tentamenVeld.setText(tentamen.getNaam());
    }

    public boolean isOkClicked() {
        return okClicked;
    }

    @FXML
    protected void handleAnnulerenButtonAction(ActionEvent event)
    {
        dialogStage.close();
    }


    /**
     * Bij het klikken op 'klaarzetten', maak en nieuwe klaargezetTentamen object en stuur deze door naar de backend.
     * Geef een warning als niet alle gegevens zijn ingevuld
     * @param event
     */
    @FXML
    protected void handleKlaarzettenButtonAction(ActionEvent event) {
        if (isInputValid()) {

            Date van = getDate(vanDateVeld);
            Date tot = getDate(totDateVeld);

            KlaargezetTentamen klaargezetTentamen = new KlaargezetTentamen(tentamen, van, tot, sleutelVeld.getText());

            runIfNotNull(onKlaarzettenTentamen, klaargezetTentamen);

            okClicked = true;
            dialogStage.close();
        } else {
            //warning..
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(PrimaryStageConfig.getInstance().getPrimaryStage());
            String errorWarning = "Error warning";
            alert.setTitle(errorWarning);
            String warningMessage = "Vul alle velden in!";
            alert.setHeaderText(warningMessage);

            alert.showAndWait();
        }
    }

    /**
     * Methode om DateTime fields om te zette naar Date
     *
     * @param field
     * @return
     */
    public Date getDate(DateTimePicker field) {
        LocalDateTime ldt = field.getDateTimeValue();
        Date out = Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());
        return out;
    }

    /**
     * Controleert of de input valide is.
     * @return
     */
    private boolean isInputValid(){
        if(tentamenVeld.getText() != null && vanDateVeld.getValue() != null && totDateVeld.getValue() != null && sleutelVeld.getText() != null)
            return true;
        else
            return false;
    }

    public void setOnKlaarzettenTentamen(Consumer<KlaargezetTentamen> onTentamenKlaargezet) {
        this.onKlaarzettenTentamen = onTentamenKlaargezet;
    }
}
