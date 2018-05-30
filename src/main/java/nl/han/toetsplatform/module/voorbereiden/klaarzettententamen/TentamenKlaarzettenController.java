package nl.han.toetsplatform.module.voorbereiden.klaarzettententamen;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class TentamenKlaarzettenController {

    @FXML
    private Label lblTentamen;

    @FXML
    private DatePicker beginVeld;

    @FXML
    private DatePicker eindVeld;

    @FXML
    private TextField sleutelVeld;

    private Tentamen tentamen;
    private Stage dialogStage;
    private boolean okClicked = false;

    @FXML
    private void initialize(){
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setTentamen(Tentamen tentamen) {
        this.tentamen = tentamen;

        lblTentamen.setText(tentamen.getNaam());
        //beginVeld.set(tentamen.getTot());
        //eindVeld.setText(tentamen.getVan());
        sleutelVeld.setText(tentamen.getSleutel());
    }

    public boolean isOkClicked() {
        return okClicked;
    }

    @FXML
    protected void handleAnnulerenButtonAction(ActionEvent event)
    {
        dialogStage.close();
    }

    @FXML
    protected void handleKlaarzettenButtonAction(ActionEvent event) {
        if (isInputValid()) {
            tentamen.setNaam(lblTentamen.getText());

            okClicked = true;
            dialogStage.close();
        }
    }

    private boolean isInputValid(){
        return true;
    }
}
