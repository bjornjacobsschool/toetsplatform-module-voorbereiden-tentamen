package nl.han.toetsplatform.module.voorbereiden.controllers.samenstellen;

import com.cathive.fx.guice.GuiceFXMLLoader;
import com.google.gson.Gson;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import nl.han.toetsplatform.module.voorbereiden.models.Vraag;
import nl.han.toetsplatform.module.voorbereiden.models.VraagTest;

import static nl.han.toetsplatform.module.voorbereiden.util.RunnableUtil.runIfNotNull;

public class SamenstellenController {
    public AnchorPane childPane;
    public GridPane vragenPane;

    Runnable vraagToevoegen;
    Runnable onTentamenOpslaan;
    Runnable onAnnuleren;

    public void setOnTentamenOpslaan(Runnable onTentamenOpslaan) {
        this.onTentamenOpslaan = onTentamenOpslaan;
    }

    public void setVraagToevoegen(Runnable vraagToevoegen) {
        this.vraagToevoegen = vraagToevoegen;
    }

    public void setOnAnnuleren(Runnable onAnnuleren) {
        this.onAnnuleren = onAnnuleren;
    }

    @FXML
    protected void initialize() {
        vragenPane.setStyle("-fx-border-style: solid");
    }

    @FXML
    protected void handleVraagToevoegenButtonAction(ActionEvent event) {
        runIfNotNull(vraagToevoegen);
    }

    public void voegVraagToe(Vraag vraag){
        //Gson gson = new Gson();
        VraagTest vraagText = new Gson().fromJson(vraag.getVraagData(), VraagTest.class);
        vragenPane.getChildren().add(new Label(vraagText.vraagText));
    }

    @FXML
    protected void handleAnnulerenButtonAction(ActionEvent event) {
        runIfNotNull(onAnnuleren);

        // actie voor annuleren
    }

    @FXML
    public void handleTentamenOpslaanButtonAction(ActionEvent event) {

        runIfNotNull(onTentamenOpslaan);
        // actie voor voorblad aanmaken
    }
}
