package nl.han.toetsplatform.module.voorbereiden.controllers.samenstellen;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import nl.han.toetsplatform.module.shared.plugin.Plugin;
import nl.han.toetsplatform.module.shared.plugin.PluginLoader;
import nl.han.toetsplatform.module.voorbereiden.models.Vraag;

import java.util.UUID;
import java.util.function.Consumer;

import static nl.han.toetsplatform.module.voorbereiden.util.RunnableUtil.runIfNotNull;


public class VraagOpstelController {

    public AnchorPane opstelContainer;
    public TextField naamField;
    public TextField themaField;
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
        naamField.setText(vraag.getNaam());
        themaField.setText(vraag.getThema());

        try {
            plugin = PluginLoader.getPlugin(vraag.getVraagtype());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Node view = plugin.getVraagCreatorView().getView();
        AnchorPane.setTopAnchor(view, 0D);
        AnchorPane.setBottomAnchor(view, 0D);
        AnchorPane.setLeftAnchor(view, 0D);
        AnchorPane.setRightAnchor(view, 0D);
        opstelContainer.getChildren().add(view);
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
        vraag.setId(UUID.randomUUID());
        vraag.setNaam(naamField.getText());
        vraag.setThema(themaField.getText());

        runIfNotNull(onVraagSave, vraag);
    }
}
