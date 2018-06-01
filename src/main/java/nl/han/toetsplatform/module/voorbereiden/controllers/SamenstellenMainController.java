package nl.han.toetsplatform.module.voorbereiden.controllers;

import com.cathive.fx.guice.GuiceFXMLLoader;
import javafx.concurrent.Task;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import nl.han.toetsplatform.module.voorbereiden.applicationlayer.ITentamenSamenstellen;
import nl.han.toetsplatform.module.voorbereiden.config.ConfigTentamenVoorbereidenModule;
import nl.han.toetsplatform.module.voorbereiden.config.SamenstellenTentamenFXMLFiles;
import nl.han.toetsplatform.module.voorbereiden.exceptions.GatewayCommunicationException;
import nl.han.toetsplatform.module.voorbereiden.models.Tentamen;
//import nl.han.toetsplatform.module.voorbereiden.models.Vraag;

import javax.inject.Inject;
import java.io.IOException;
import java.sql.SQLException;

public class SamenstellenMainController {
    public AnchorPane mainContainer;
    GuiceFXMLLoader fxmlLoader;
    GuiceFXMLLoader.Result samenStellenView;
    private ITentamenSamenstellen _ITentamenSamenstellen;

    private Tentamen tentamen;

    @Inject
    public SamenstellenMainController(GuiceFXMLLoader fxmlLoader, ITentamenSamenstellen tentamenSamenstellen) {
        this.fxmlLoader = fxmlLoader;
        this._ITentamenSamenstellen = tentamenSamenstellen;
    }

    public void initialize() throws IOException {
        GuiceFXMLLoader.Result voorbladView = fxmlLoader.load(ConfigTentamenVoorbereidenModule.getFXMLTentamenUitvoeren(SamenstellenTentamenFXMLFiles.TentamenSamenstellenVoorblad), null);
        setAnchorFull(voorbladView.getRoot());
        mainContainer.getChildren().add(voorbladView.getRoot());
        VoorbladController voorbladController = voorbladView.getController();
        voorbladController.setOnVoorbladAanmaken(this::onVoorbladAangemaakt);
    }

    public void onVoorbladAangemaakt(Tentamen voorblad){
        try {
            tentamen = voorblad;

            samenStellenView = fxmlLoader.load(ConfigTentamenVoorbereidenModule.getFXMLTentamenUitvoeren(SamenstellenTentamenFXMLFiles.TentamenSamenstellen), null);
            showSamenstellenTentamen();
            SamenstellenController samenstellenController = samenStellenView.getController();
            samenstellenController.setOnTentamenOpslaan(this::onTentamenAangemaakt);
            samenstellenController.setVraagToevoegen(this::vraagToevoegen);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void vraagToevoegen(){
        try {
            GuiceFXMLLoader.Result vraagOpstellenView = fxmlLoader.load(ConfigTentamenVoorbereidenModule.getFXMLTentamenUitvoeren(SamenstellenTentamenFXMLFiles.OpstellenVraag), null);
            mainContainer.getChildren().clear();
            setAnchorFull(vraagOpstellenView.getRoot());
            mainContainer.getChildren().add(vraagOpstellenView.getRoot());
            VraagOpstelController vraagOpstelController = vraagOpstellenView.getController();
            nl.han.toetsapplicatie.module.model.Vraag moduleVraag = new nl.han.toetsapplicatie.module.model.Vraag();
            moduleVraag.setPlugin("nl.han.toetsapplicatie.plugin.GraphPlugin");
            vraagOpstelController.setVraag(moduleVraag);
            vraagOpstelController.onVraagSave = (vraag) -> {
                SamenstellenController samenstellenController = samenStellenView.getController();
                samenstellenController.voegVraagToe(vraag);

                tentamen.getVragen().add(vraag);
                showSamenstellenTentamen();
            };

            vraagOpstelController.onAnnuleer = () ->{
                showSamenstellenTentamen();
            };
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void onTentamenAangemaakt() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText(null);

        Task task = new Task<Void>() {
            @Override
            public Void call() {
                try {
                    _ITentamenSamenstellen.opslaan(tentamen);
                } catch (GatewayCommunicationException e) {
                    alert.setAlertType(Alert.AlertType.ERROR);
                    alert.setContentText(e.getMessage());
                } catch (SQLException e) {
                    alert.setAlertType(Alert.AlertType.ERROR);
                    alert.setContentText(e.getMessage());
                }
                return null;
            }
        };

        alert.setContentText("Tentamen is opgeslagen");
        task.setOnSucceeded(taskFinishEvent -> alert.showAndWait());
        new Thread(task).start();

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
