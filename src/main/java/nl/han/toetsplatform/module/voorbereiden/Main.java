package nl.han.toetsplatform.module.voorbereiden;

import com.cathive.fx.guice.GuiceApplication;
import com.cathive.fx.guice.GuiceFXMLLoader;
import com.google.inject.Module;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import nl.han.toetsplatform.module.voorbereiden.config.ConfigTentamenVoorbereidenModule;
import nl.han.toetsplatform.module.voorbereiden.config.PrimaryStageConfig;
import nl.han.toetsplatform.module.voorbereiden.config.TentamenVoorbereidenFXMLFiles;
import nl.han.toetsplatform.module.voorbereiden.guice.StubGuiceModule;

import javax.inject.Inject;
import java.util.List;


public class Main extends GuiceApplication {

    @Inject
    private GuiceFXMLLoader fxmlLoader;

    @Override
    public void start(Stage primaryStage) throws Exception{

        Parent root = fxmlLoader.load(ConfigTentamenVoorbereidenModule.getFXMLTentamenVoorbereiden(TentamenVoorbereidenFXMLFiles.VoorbereidenMain), null).getRoot();
        primaryStage.setTitle("Docent Applicatie");
        primaryStage.setScene(new Scene(root, 1280, 720));
        primaryStage.show();

        PrimaryStageConfig.getInstance().setPrimaryStage(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void init(List<Module> modules) {
        modules.add(ConfigTentamenVoorbereidenModule.getModule());
        modules.add(new StubGuiceModule());
    }
}
