package nl.han.toetsplatform.module.voorbereiden.controllers.samenstellen;

import javafx.event.ActionEvent;

import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
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

    Runnable onGeannuleerd;
    Consumer<Vraag> onVraagSave;

    public void setOnVraagSave(Consumer<Vraag> onVraagSave) {
        this.onVraagSave = onVraagSave;
    }

    public void setOnAnnuleren(Runnable onAnnuleer) {

        this.onGeannuleerd = onAnnuleer;
    }

    public void setVraag(Vraag vraag) {
        this.vraag = vraag;
//        lblVraagName.setText(vraag.getNaam());
        try {
            // todo pluginloader moet vraag van module voorbereiden tentamen gaan gebruiken i.p.v. toetsapplicatie. Insert the correct vraag when pluginloader is fixed
            plugin = PluginLoader.getPlugin(null);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        opstelContainer.getChildren().add(plugin.getVraagCreatorView().getView());
    }

    public void btnAnnuleerPressed(ActionEvent event) {
        runIfNotNull(onGeannuleerd);
    }

    public void btnOpslaanPressed(ActionEvent event){
        vraag.setVraagData(plugin.getVraagCreatorView().getQuestionData());
        vraag.setId("9");
        vraag.setNaam("Test naam");

        runIfNotNull(onVraagSave, vraag);
    }
}
