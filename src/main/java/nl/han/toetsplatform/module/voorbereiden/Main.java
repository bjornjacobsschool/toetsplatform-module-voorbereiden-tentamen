package nl.han.toetsplatform.module.voorbereiden;

import com.cathive.fx.guice.GuiceApplication;
import com.cathive.fx.guice.GuiceFXMLLoader;
import com.google.inject.Module;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import nl.han.toetsplatform.module.shared.storage.StorageDao;
import nl.han.toetsplatform.module.voorbereiden.config.ConfigTentamenVoorbereidenModule;
import nl.han.toetsplatform.module.voorbereiden.config.SamenstellenTentamenFXMLFiles;
import nl.han.toetsplatform.module.voorbereiden.data.SqlLoader;
import nl.han.toetsplatform.module.voorbereiden.guice.GuiceModule;
import nl.han.toetsplatform.module.voorbereiden.guice.StubGuiceModule;

import javax.inject.Inject;
import java.util.List;

public class Main extends GuiceApplication {

    @Inject
    private GuiceFXMLLoader fxmlLoader;

    @Inject
    StorageDao storageDao;

    @Inject
    SqlLoader sqlLoader;

    @Override
    public void start(Stage primaryStage) throws Exception{
        storageDao.executeUpdate(sqlLoader.load("DDL"));

        Parent root = fxmlLoader.load(ConfigTentamenVoorbereidenModule.getFXMLTentamenUitvoeren(SamenstellenTentamenFXMLFiles.SamenstellenMain), null).getRoot();
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 1280, 720));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void init(List<Module> modules) throws Exception {
        modules.add(ConfigTentamenVoorbereidenModule.getModule());
        modules.add(new StubGuiceModule());
    }
}
