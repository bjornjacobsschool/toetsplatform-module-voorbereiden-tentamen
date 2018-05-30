package nl.han.toetsplatform.module.voorbereiden.samenstellententamen.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class VoorbladController {

    @FXML
    protected void handleAnnulerenButtonAction(ActionEvent event)
    {
        System.out.println("Annuleren");
        // actie voor annuleren
    }

    @FXML
    protected void handleVoorbladAanmakenButtonAction(ActionEvent event)
    {
        System.out.println("Voorblad aanmaken");
        // actie voor voorblad aanmaken
    }
}
