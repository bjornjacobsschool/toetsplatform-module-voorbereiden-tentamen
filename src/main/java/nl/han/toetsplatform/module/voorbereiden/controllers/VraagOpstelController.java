package nl.han.toetsplatform.module.voorbereiden.controllers;

import javafx.event.ActionEvent;

import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import nl.han.toetsplatform.module.voorbereiden.data.VraagOpslaanDAO;
import nl.han.toetsapplicatie.module.plugin.Plugin;
import nl.han.toetsapplicatie.module.plugin.PluginLoader;
import nl.han.toetsplatform.module.voorbereiden.models.Vraag;

import java.util.function.Consumer;

import static nl.han.toetsplatform.module.voorbereiden.util.RunnableUtil.runIfNotNull;


public class VraagOpstelController {

    public Label lblVraagName;
    public AnchorPane opstelContainer;
    Vraag vraag;
    Plugin plugin;

    Runnable onAnnuleer;
    Consumer<Vraag> onVraagSave;

    public void setOnVraagSave(Consumer<Vraag> onVraagSave) {
        this.onVraagSave = onVraagSave;
    }

    public void setOnExit(Runnable onExit) {
        this.onAnnuleer = onExit;
    }

    public void setVraag(Vraag vraag) {
        this.vraag = vraag;
        lblVraagName.setText(vraag.getNaam());
       // try {
           // plugin = PluginLoader.getPlugin(vraag);
       // } catch (ClassNotFoundException e) {
       //     e.printStackTrace();
      //  }
        opstelContainer.getChildren().add(plugin.getVraagCreatorView().getView());
    }

    public void btnAnnuleerPressed(ActionEvent event) {
        runIfNotNull(onAnnuleer);
    }

    public void btnOpslaanPressed(ActionEvent event){
//      String vraagData = plugin.getVraagCreatorView().getQuestionData();

      Vraag vraag = new Vraag();
      vraag.setNaam("Test naam");

      runIfNotNull(onVraagSave, vraag);
    }
}
