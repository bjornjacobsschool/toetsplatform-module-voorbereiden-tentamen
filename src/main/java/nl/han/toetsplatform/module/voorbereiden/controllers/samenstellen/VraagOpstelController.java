package nl.han.toetsplatform.module.voorbereiden.controllers.samenstellen;

import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import nl.han.toetsplatform.module.shared.plugin.Plugin;
import nl.han.toetsplatform.module.shared.plugin.PluginLoader;
import nl.han.toetsplatform.module.voorbereiden.models.Vraag;

import java.util.UUID;
import java.util.function.Consumer;

import static nl.han.toetsplatform.module.voorbereiden.util.RunnableUtil.runIfNotNull;


public class VraagOpstelController {

    public Label lblVraagName;
    public AnchorPane opstelContainer;
    private Plugin plugin;

    private Vraag vraag;

    private Runnable onAnnuleer;
    private Consumer<Vraag> onVraagSave;

    /**
     * Setter
     * @param onVraagSave
     */
    public void setOnVraagSave(Consumer<Vraag> onVraagSave) {
        this.onVraagSave = onVraagSave;
    }

    /**
     * Setter voor OnExit
     * @param onExit
     */
    public void setOnExit(Runnable onExit) {
        this.onAnnuleer = onExit;
    }

    /**
     * Methode die de vraag set en de bijbehorende plugin ophaalt.
     * @param vraag
     */
    public void setVraag(Vraag vraag) {
        this.vraag = vraag;
        try {
            plugin = PluginLoader.getPlugin(vraag.getVraagType(), vraag.getVraagData());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        opstelContainer.getChildren().add(plugin.getVraagCreatorView().getView());
    }

    /**
     * Actie voor het klikken op annuleren.
     * @param event
     */
    public void btnAnnuleerPressed(ActionEvent event) {
        runIfNotNull(onAnnuleer);
    }

    /**
     * Actie voor het klikken op Opslaan
     * @param event
     */
    public void btnOpslaanPressed(ActionEvent event){
        vraag.setVraagData(plugin.getVraagCreatorView().getQuestionData());
        vraag.setId(UUID.randomUUID().toString());
        vraag.setNaam("Test naam");

        runIfNotNull(onVraagSave, vraag);
    }
}
