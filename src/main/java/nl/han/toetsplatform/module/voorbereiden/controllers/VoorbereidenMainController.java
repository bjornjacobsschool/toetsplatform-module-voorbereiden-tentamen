package nl.han.toetsplatform.module.voorbereiden.controllers;

import com.cathive.fx.guice.GuiceFXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import nl.han.toetsplatform.module.voorbereiden.config.ConfigTentamenVoorbereidenModule;
import nl.han.toetsplatform.module.voorbereiden.config.TentamenVoorbereidenFXMLFiles;
import nl.han.toetsplatform.module.voorbereiden.controllers.overzicht.TentamenOverzichtController;
import nl.han.toetsplatform.module.voorbereiden.controllers.samenstellen.SamenstellenMainController;

import javax.inject.Inject;
import java.io.IOException;

public class VoorbereidenMainController {

    private final GuiceFXMLLoader fxmlLoader;
    private GuiceFXMLLoader.Result overzichtView;
    private GuiceFXMLLoader.Result samenstellenView;

    public AnchorPane mainContainer;

    @Inject
    public VoorbereidenMainController(GuiceFXMLLoader fxmlLoader) {
        this.fxmlLoader = fxmlLoader;
    }

    /**
     * Laad de overzicht pagina in
     * @throws IOException
     */
    public void initialize() throws IOException {
        overzichtView = fxmlLoader.load(ConfigTentamenVoorbereidenModule.getFXMLTentamenVoorbereiden(TentamenVoorbereidenFXMLFiles.TentamenOverzicht));
        mainContainer.getChildren().add(overzichtView.getRoot());
        setAnchorFull(overzichtView.getRoot());
        TentamenOverzichtController overzichtController = overzichtView.getController();
        overzichtController.setOnNieuwTentamen(this::onNieuwTentamen);
    }

    /**
     * Methode die de samenstellen view
     */
    public void onNieuwTentamen(){
        System.out.println("Nieuw");
        try {
            samenstellenView = fxmlLoader.load(ConfigTentamenVoorbereidenModule.getFXMLTentamenVoorbereiden(TentamenVoorbereidenFXMLFiles.SamenstellenMain));
        } catch (IOException e) {
            e.printStackTrace();
        }
        switchView(samenstellenView);
        SamenstellenMainController samenstellenMainController = samenstellenView.getController();
        samenstellenMainController.setOnAnnulerenVoorblad(this::onAnnulerenSamenstellen);
        samenstellenMainController.setOnTentamenAangemaakt(this::onTentamenAangemaakt);

    }

    private void onTentamenAangemaakt() {
        switchView(overzichtView);
        ((TentamenOverzichtController) overzichtView.getController()).refreshOverzicht();
    }

    private void onAnnulerenSamenstellen() {
        switchView(overzichtView);
    }


    /**
     * Helper methode om van view te switchen.
     * @param view
     */
    private void switchView(GuiceFXMLLoader.Result view) {
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
}
