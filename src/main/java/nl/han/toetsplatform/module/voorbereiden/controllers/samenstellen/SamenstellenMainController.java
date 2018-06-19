package nl.han.toetsplatform.module.voorbereiden.controllers.samenstellen;

import com.cathive.fx.guice.GuiceFXMLLoader;
import javafx.concurrent.Task;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import nl.han.toetsapplicatie.apimodels.dto.SamengesteldTentamenDto;
import nl.han.toetsapplicatie.apimodels.dto.VragenbankVraagDto;
import nl.han.toetsplatform.module.voorbereiden.Main;
import nl.han.toetsplatform.module.voorbereiden.applicationlayer.ITentamenSamenstellen;
import nl.han.toetsplatform.module.voorbereiden.config.ConfigTentamenVoorbereidenModule;
import nl.han.toetsplatform.module.voorbereiden.config.TentamenVoorbereidenFXMLFiles;
import nl.han.toetsplatform.module.voorbereiden.exceptions.GatewayCommunicationException;
import nl.han.toetsplatform.module.voorbereiden.util.TentamenFile;

import javax.inject.Inject;
import java.io.IOException;
import java.sql.SQLException;

import static nl.han.toetsplatform.module.voorbereiden.util.RunnableUtil.runIfNotNull;


public class SamenstellenMainController {
    public AnchorPane mainContainer;
    private GuiceFXMLLoader fxmlLoader;
    private GuiceFXMLLoader.Result samenStellenView;
    private GuiceFXMLLoader.Result voorbladView;
    private ITentamenSamenstellen _tentamenSamenstellen;
    private SamengesteldTentamenDto tentamen;
    private Runnable onTentamenAangemaakt;
    private Runnable onAnnulerenVoorblad;

    @Inject
    public SamenstellenMainController(GuiceFXMLLoader fxmlLoader, ITentamenSamenstellen tentamenSamenstellen, TentamenFile tentamenFile) {
        this.fxmlLoader = fxmlLoader;
        this._tentamenSamenstellen = tentamenSamenstellen;
    }

    /**
     * Laad de voorbladview in voor het samenstellen van een tentamen
     * @throws IOException
     */
    public void initialize() throws IOException {
        voorbladView = fxmlLoader.load(ConfigTentamenVoorbereidenModule.getFXMLTentamenVoorbereiden(TentamenVoorbereidenFXMLFiles.TentamenSamenstellenVoorblad), null);
        setAnchorFull(voorbladView.getRoot());
        mainContainer.getChildren().add(voorbladView.getRoot());
        VoorbladController voorbladController = voorbladView.getController();
        voorbladController.setOnVoorbladAanmaken(this::onVoorbladAangemaakt);
        voorbladController.setOnGeannuleerd(this::onAnnulerenVoorblad);
    }

    /**
     * Methode die de view van het toevoegen van een vraag inlaad en de benodigde gegevens meestuurt.
     */
    public void vraagToevoegen() {
        try {
            String pluginType = getPluginType();
            if (pluginType == null) return;

            GuiceFXMLLoader.Result vraagOpstellenView = fxmlLoader.load(ConfigTentamenVoorbereidenModule.getFXMLTentamenVoorbereiden(TentamenVoorbereidenFXMLFiles.OpstellenVraag), null);
            showView(vraagOpstellenView);
            VraagOpstelController vraagOpstelController = vraagOpstellenView.getController();

            //Maak een nieuwe vraag en geef deze mee aan de controller.
            VragenbankVraagDto moduleVraag = new VragenbankVraagDto();
            moduleVraag.setVraagtype(pluginType);

            vraagOpstelController.setVraag(moduleVraag);
            vraagOpstelController.setOnVraagSave(this::onVraagToevoegen);
            vraagOpstelController.setOnExit(this::onAnnulerenVraagToevoegen);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Op annuleren van het toevoegen van een vraag toon de samenstellenView.
     */
    private void onAnnulerenVraagToevoegen() {
        showView(samenStellenView);
    }

    /**
     * Op het toevoegen van een vraag
     * @param vraag
     */
    private void onVraagToevoegen(VragenbankVraagDto vraag) {
        _tentamenSamenstellen.slaVraagOp(vraag);
        SamenstellenController samenstellenController = samenStellenView.getController();
        samenstellenController.setVragen(_tentamenSamenstellen.getVragen());
        showView(samenStellenView);
    }

    /**
     * Opent een dialoog voor het kiezen van een plugin
     *
     * @return het type plugin: null als ere niks is gekozen
     */
    private String getPluginType() {
        GuiceFXMLLoader.Result klaarzettenView;
        try {
            klaarzettenView = fxmlLoader.load(Main.class.getResource("/fxml/PluginTypeKiezen.fxml"));
            //create the dialog stage
            Stage klaarzettenPopupStage = new Stage();
            klaarzettenPopupStage.setTitle("Plugin selecteren");
            klaarzettenPopupStage.initModality(Modality.WINDOW_MODAL);
            klaarzettenPopupStage.initOwner(mainContainer.getScene().getWindow());
            Scene scene = new Scene(klaarzettenView.getRoot(), 400, 300);
            klaarzettenPopupStage.setScene(scene);

            PluginTypeKiezenController pluginTypeKiezenController = klaarzettenView.getController();
            pluginTypeKiezenController.setDialogStage(klaarzettenPopupStage);

            klaarzettenPopupStage.showAndWait();
            return pluginTypeKiezenController.getSelectedPlugin();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Actie voor het aanmaken van het voorblad.
     * @param voorblad
     */
    public void onVoorbladAangemaakt(SamengesteldTentamenDto voorblad) {
        try {
            tentamen = voorblad;

            samenStellenView = fxmlLoader.load(ConfigTentamenVoorbereidenModule.getFXMLTentamenVoorbereiden(TentamenVoorbereidenFXMLFiles.TentamenSamenstellen), null);
            showView(samenStellenView);
            SamenstellenController samenstellenController = samenStellenView.getController();
            samenstellenController.setOnTentamenOpslaan(this::onTentamenAangemaakt);
            samenstellenController.setTentamen(tentamen);
            samenstellenController.setVragen(_tentamenSamenstellen.getVragen());
            samenstellenController.setVraagToevoegen(this::vraagToevoegen);
            samenstellenController.setOnAnnuleren(this::onAnnulerenSamenstellen);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Actie voor het annuleren op de samenstellen pagina.
     */
    private void onAnnulerenSamenstellen() {
        showView(voorbladView);
    }

    /**
     * Actie voor het opslaan van een tentamen
     */
    private void onTentamenAangemaakt(SamengesteldTentamenDto tentamen) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText(null);

        Task task = new Task<Void>() {
            @Override
            public Void call() {
                try {
                    _tentamenSamenstellen.opslaan(tentamen);
                } catch (GatewayCommunicationException e) {
                    System.out.println(e.getMessage());
                    alert.setAlertType(Alert.AlertType.ERROR);
                    alert.setContentText(e.getMessage());
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                    alert.setAlertType(Alert.AlertType.ERROR);
                    alert.setContentText(e.getMessage());
                }
                return null;
            }
        };

        alert.setContentText("Tentamen is opgeslagen");
        task.setOnSucceeded(taskFinishEvent -> {
            runIfNotNull(onTentamenAangemaakt);
        });
        new Thread(task).start();
    }

    /**
     * Actie voor het annuleren op het voorblad.
     */
    private void onAnnulerenVoorblad() {
        runIfNotNull(onAnnulerenVoorblad);
    }

    /**
     * Helper method om een nieuwe view in te laden.
     * @param view
     */
    private void showView(GuiceFXMLLoader.Result view) {
        mainContainer.getChildren().clear();
        mainContainer.getChildren().add(view.getRoot());
        setAnchorFull(view.getRoot());
    }

    /**
     * Methode die het scherm vergroot
     * @param node
     */
    private void setAnchorFull(Node node){
        AnchorPane.setBottomAnchor(node, 0D);
        AnchorPane.setLeftAnchor(node, 0D);
        AnchorPane.setRightAnchor(node, 0D);
        AnchorPane.setTopAnchor(node, 0D);
    }

    /**
     * Setter
     * @param onTentamenAangemaakt
     */
    public void setOnTentamenAangemaakt(Runnable onTentamenAangemaakt) {
        this.onTentamenAangemaakt = onTentamenAangemaakt;
    }

    /**
     * Setter
     * @param onAnnulerenVoorblad
     */
    public void setOnAnnulerenVoorblad(Runnable onAnnulerenVoorblad) {
        this.onAnnulerenVoorblad = onAnnulerenVoorblad;
    }
}
