package nl.han.toetsplatform.module.voorbereiden.controllers;

import com.cathive.fx.guice.GuiceFXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import nl.han.toetsplatform.module.voorbereiden.config.ConfigTentamenVoorbereidenModule;
import nl.han.toetsplatform.module.voorbereiden.config.SamenstellenTentamenFXMLFiles;
import nl.han.toetsplatform.module.voorbereiden.models.Tentamen;
import nl.han.toetsplatform.module.voorbereiden.models.Vraag;

import javax.inject.Inject;
import java.io.IOException;

public class SamenstellenMainController {
    public AnchorPane mainContainer;

    GuiceFXMLLoader fxmlLoader;

    GuiceFXMLLoader.Result samenStellenView;

    @Inject
    public SamenstellenMainController(GuiceFXMLLoader fxmlLoader) {
        this.fxmlLoader = fxmlLoader;
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
            samenStellenView = fxmlLoader.load(ConfigTentamenVoorbereidenModule.getFXMLTentamenUitvoeren(SamenstellenTentamenFXMLFiles.TentamenSamenstellen), null);
            showSamenstellenTentamen();
            SamenstellenController samenstellenController = samenStellenView.getController();

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
            vraagOpstelController.onVraagSave = (vraag) -> {
                SamenstellenController samenstellenController = samenStellenView.getController();
                samenstellenController.voegVraagToe(vraag);

                showSamenstellenTentamen();
            };

            vraagOpstelController.onAnnuleer = () ->{
                showSamenstellenTentamen();
            };


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
