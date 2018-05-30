package nl.han.toetsplatform.module.voorbereiden.samenstellententamen.controllers;

import com.google.inject.Inject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import nl.han.toetsplatform.module.voorbereiden.classes.IInterfaceOmTeDemostrerenDatDIWerkt;

public class SamenstellenController {
    public AnchorPane childPane;
    public GridPane vragenPane;

    private IInterfaceOmTeDemostrerenDatDIWerkt _interfaceOmTeDemostrerenDatDIWerkt;

    @Inject
    public SamenstellenController(IInterfaceOmTeDemostrerenDatDIWerkt testClass) {
        this._interfaceOmTeDemostrerenDatDIWerkt = testClass;
    }


    @FXML
    protected void initialize() {
        vragenPane.setStyle("-fx-border-style: solid");
    }

    @FXML
    protected void handleVraagToevoegenButtonAction(ActionEvent event) {
        childPane.getChildren().add(new Label(_interfaceOmTeDemostrerenDatDIWerkt.getSampleText()));
        // actie voor inladen vraag toevoegen
    }

    @FXML
    protected void handleAnnulerenButtonAction(ActionEvent event) {
        System.out.println("Annuleren");
        // actie voor annuleren
    }

    @FXML
    protected void handleTentamenOpslaanButtonAction(ActionEvent event) {
        System.out.println("Tentamen opslaan");
        System.out.println(_interfaceOmTeDemostrerenDatDIWerkt.getSampleText());
        // actie voor voorblad aanmaken
    }
}
