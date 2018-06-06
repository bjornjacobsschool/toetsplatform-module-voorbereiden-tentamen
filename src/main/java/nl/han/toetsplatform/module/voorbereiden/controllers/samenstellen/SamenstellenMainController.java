package nl.han.toetsplatform.module.voorbereiden.controllers.samenstellen;

import com.cathive.fx.guice.GuiceFXMLLoader;
import javafx.concurrent.Task;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import nl.han.toetsplatform.module.voorbereiden.applicationlayer.ITentamenSamenstellen;
import nl.han.toetsplatform.module.voorbereiden.config.ConfigTentamenVoorbereidenModule;
import nl.han.toetsplatform.module.voorbereiden.config.PrimaryStageConfig;
import nl.han.toetsplatform.module.voorbereiden.config.TentamenVoorbereidenFXMLFiles;
import nl.han.toetsplatform.module.voorbereiden.exceptions.GatewayCommunicationException;
import nl.han.toetsplatform.module.voorbereiden.models.Tentamen;
import nl.han.toetsplatform.module.voorbereiden.models.Vraag;
import nl.han.toetsplatform.module.voorbereiden.util.TentamenFile;

import javax.inject.Inject;
import java.io.IOException;
import java.sql.SQLException;


public class SamenstellenMainController {
    public AnchorPane mainContainer;
    private GuiceFXMLLoader fxmlLoader;
    private GuiceFXMLLoader.Result samenStellenView;
    private ITentamenSamenstellen _tentamenSamenstellen;
    private Tentamen tentamen;
    private GuiceFXMLLoader.Result voorbladView;

    @Inject
    public SamenstellenMainController(GuiceFXMLLoader fxmlLoader, ITentamenSamenstellen tentamenSamenstellen, TentamenFile tentamenFile) {
        this.fxmlLoader = fxmlLoader;
        this._tentamenSamenstellen = tentamenSamenstellen;
    }

    public void initialize() throws IOException {
        voorbladView = fxmlLoader.load(ConfigTentamenVoorbereidenModule.getFXMLTentamenVoorbereiden(TentamenVoorbereidenFXMLFiles.TentamenSamenstellenVoorblad), null);
        setAnchorFull(voorbladView.getRoot());
        mainContainer.getChildren().add(voorbladView.getRoot());
        VoorbladController voorbladController = voorbladView.getController();
        voorbladController.setOnVoorbladAanmaken(this::onVoorbladAangemaakt);
        voorbladController.setOnGeannuleerd(this::onAnnulerenVoorblad);
    }

    /**
     * Methode die het toevoegen van een vraag inlaad.
     */
    public void vraagToevoegen(){
        try {
            GuiceFXMLLoader.Result vraagOpstellenView = fxmlLoader.load(ConfigTentamenVoorbereidenModule.getFXMLTentamenVoorbereiden(TentamenVoorbereidenFXMLFiles.OpstellenVraag), null);
            mainContainer.getChildren().clear();
            setAnchorFull(vraagOpstellenView.getRoot());
            mainContainer.getChildren().add(vraagOpstellenView.getRoot());
            VraagOpstelController vraagOpstelController = vraagOpstellenView.getController();
            Vraag moduleVraag = new Vraag();
            moduleVraag.setVraagType("nl.han.toetsapplicatie.plugin.GraphPlugin");
            vraagOpstelController.setVraag(moduleVraag);
            vraagOpstelController.onVraagSave = (vraag) -> {
                SamenstellenController samenstellenController = samenStellenView.getController();
                samenstellenController.setTentamen(tentamen);
                samenstellenController.voegVraagToe(vraag);

                //tentamen.getVragen().add(vraag);
                showSamenstellenTentamen();
            };
            vraagOpstelController.onAnnuleer = () ->{
                showSamenstellenTentamen();
            };
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Actie voor het aanmaken van het voorblad.
     * @param voorblad
     */
    public void onVoorbladAangemaakt(Tentamen voorblad){
        try {
            tentamen = voorblad;

            samenStellenView = fxmlLoader.load(ConfigTentamenVoorbereidenModule.getFXMLTentamenVoorbereiden(TentamenVoorbereidenFXMLFiles.TentamenSamenstellen), null);
            showSamenstellenTentamen();
            SamenstellenController samenstellenController = samenStellenView.getController();
            samenstellenController.setOnTentamenOpslaan(this::onTentamenAangemaakt);
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
        mainContainer.getChildren().clear();
        setAnchorFull(voorbladView.getRoot());
        mainContainer.getChildren().add(voorbladView.getRoot());
    }

    /**
     * Actie voor het opslaan van een tentamen
     */
    private void onTentamenAangemaakt(Tentamen tentamen) {
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
                for(Vraag v: tentamen.getVragen()){
                    System.out.println(v.getPunten());
                }
                return null;
            }
        };

        alert.setContentText("Tentamen is opgeslagen");
        task.setOnSucceeded(taskFinishEvent -> {
            showPage(TentamenVoorbereidenFXMLFiles.TentamenOverzicht);
        });
        new Thread(task).start();
    }

    /**
     * Actie voor het annuleren op het voorblad.
     */
    private void onAnnulerenVoorblad() {
        showPage(TentamenVoorbereidenFXMLFiles.TentamenOverzicht);
    }

    /**
     * Helper method to load a different view.
     * @param view
     */
    public void showPage(TentamenVoorbereidenFXMLFiles view) {
        Stage primaryStage = PrimaryStageConfig.getInstance().getPrimaryStage();
        Parent root = null;
        try {
            root = fxmlLoader.load(ConfigTentamenVoorbereidenModule.getFXMLTentamenVoorbereiden(view), null).getRoot();
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
            PrimaryStageConfig.getInstance().setPrimaryStage(primaryStage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showSamenstellenTentamen() {
        mainContainer.getChildren().clear();
        mainContainer.getChildren().add(samenStellenView.getRoot());
        setAnchorFull(samenStellenView.getRoot());
    }

    private void setAnchorFull(Node node){
        AnchorPane.setBottomAnchor(node, 0D);
        AnchorPane.setLeftAnchor(node, 0D);
        AnchorPane.setRightAnchor(node, 0D);
        AnchorPane.setTopAnchor(node, 0D);
    }


}
