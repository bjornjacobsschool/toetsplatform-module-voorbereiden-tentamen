package nl.han.toetsplatform.module.voorbereiden.controllers;

import com.google.gson.Gson;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import nl.han.toetsapplicatie.module.model.Vraag;
import nl.han.toetsplatform.module.voorbereiden.models.Tentamen;
import nl.han.toetsplatform.module.voorbereiden.models.VraagTest;

import java.util.function.Consumer;

import static nl.han.toetsplatform.module.voorbereiden.util.RunnableUtil.runIfNotNull;

public class SamenstellenController {
    public AnchorPane childPane;
    public GridPane vragenPane;

    Runnable vraagToevoegen;
    Runnable onTentamenOpslaan;

    public void setOnTentamenOpslaan(Runnable onTentamenOpslaan) {
        this.onTentamenOpslaan = onTentamenOpslaan;
    }

    public void setVraagToevoegen(Runnable vraagToevoegen) {
        this.vraagToevoegen = vraagToevoegen;
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
        VraagTest vraagText = new Gson().fromJson(vraag.getData(), VraagTest.class);
        vragenPane.getChildren().add(new Label(vraagText.vraagText));
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
        runIfNotNull(onTentamenOpslaan);
        // actie voor voorblad aanmaken
    }
}
