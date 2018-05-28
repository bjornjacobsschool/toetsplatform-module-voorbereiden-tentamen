package main.nl.han.toetsplatform.module.voorbereiden.samenstellententamen;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class Controller {

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
