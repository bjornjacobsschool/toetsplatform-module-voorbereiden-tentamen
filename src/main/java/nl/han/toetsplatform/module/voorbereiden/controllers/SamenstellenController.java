package nl.han.toetsplatform.module.voorbereiden.controllers;

import com.google.inject.Inject;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import nl.han.toetsplatform.module.voorbereiden.applicationlayer.IInterfaceOmTeDemostrerenDatDIWerkt;

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
        Label label = new Label(_interfaceOmTeDemostrerenDatDIWerkt.getSampleText());
        label.setId("toegevoegdeLabel");
        childPane.getChildren().add(label);
        // actie voor inladen vraag toevoegen
    }

    @FXML
    protected void handleAnnulerenButtonAction(ActionEvent event) {
        System.out.println("Annuleren");

        Label label = new Label("Running...");
        label.setId("asyncLabel");
        childPane.getChildren().add(label);

            Task task = new Task<String>() {
                @Override
                public String call() {
                    //SIMULATE A FILE DOWNLOAD
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return "henk";
                }
            };

            task.setOnSucceeded(taskFinishEvent -> label.setText(((Task<String>) task).getValue()));
            new Thread(task).start();


        // actie voor annuleren
    }

    @FXML
    public void handleTentamenOpslaanButtonAction(ActionEvent event) {
        System.out.println("Tentamen opslaan");
        // actie voor voorblad aanmaken
    }
}
