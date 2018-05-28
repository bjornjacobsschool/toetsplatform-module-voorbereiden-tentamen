package nl.han.toetsplatform.module.voorbereiden.samenstellententamen.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.GridPane;

public class SamenstellenController {
    public AnchorPane childPane;
    public GridPane vragenPane;

    @FXML
    protected void initialize() {
        vragenPane.setStyle("-fx-border-style: solid");
    }

    @FXML
    protected void handleVraagToevoegenButtonAction(ActionEvent event) {
        System.out.println("Vraag toevoegen");
        childPane.getChildren().add(new Label("Dit is een hele grote label die straks gevuld moet worden met een vraag"));
        // actie voor inladen vraag toevoegen
    }

    @FXML
    protected void handleAnnulerenButtonAction(ActionEvent event)
    {
        System.out.println("Annuleren");
        // actie voor annuleren
    }

    @FXML
    protected void handleTentamenOpslaanButtonAction(ActionEvent event)
    {
        System.out.println("Tentamen opslaan");
        // actie voor voorblad aanmaken
    }
}
