package nl.han.toetsplatform.module.voorbereiden.controllers.samenstellen;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import nl.han.toetsapplicatie.apimodels.dto.VersieDto;
import nl.han.toetsapplicatie.apimodels.dto.VragenbankVraagDto;
import nl.han.toetsplatform.module.shared.plugin.Plugin;
import nl.han.toetsplatform.module.shared.plugin.PluginLoader;
import nl.han.toetsplatform.module.shared.plugin.VraagCreatorView;

import java.util.UUID;
import java.util.function.Consumer;

import static nl.han.toetsplatform.module.voorbereiden.util.RunnableUtil.runIfNotNull;


public class VraagOpstelController {

    public AnchorPane opstelContainer;
    public TextField naamField;
    public TextField themaField;
    private Plugin plugin;

    private VragenbankVraagDto vraag;

    private Runnable onAnnuleer;
    private Consumer<VragenbankVraagDto> onVraagSave;

    VraagCreatorView vraagCreatorView;

    /**
     * Setter
     * @param onVraagSave
     */
    public void setOnVraagSave(Consumer<VragenbankVraagDto> onVraagSave) {
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
    public void setVraag(VragenbankVraagDto vraag) {
        this.vraag = vraag;
        naamField.setText(vraag.getNaam());
        themaField.setText(vraag.getThema());

        try {
            plugin = PluginLoader.getPlugin(vraag.getVraagtype());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        vraagCreatorView = plugin.getVraagCreatorView();
        Node view= vraagCreatorView.getView();
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
        vraag.setVraagData(vraagCreatorView.getQuestionData());
        vraag.setId(UUID.randomUUID());
        vraag.setNaam(naamField.getText());
        vraag.setThema(themaField.getText());
        vraag.setNakijkModel("");
        vraag.setPunten(5);
        vraag.setNakijkInstructies("");
        VersieDto versie = new VersieDto();
        versie.setNummer(1);
        versie.setOmschrijving("");
        versie.setDatum(System.currentTimeMillis());
        vraag.setVersie(versie);

        runIfNotNull(onVraagSave, vraag);
    }
}
