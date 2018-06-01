package nl.han.toetsplatform.module.voorbereiden.ui.windows;

import com.cathive.fx.guice.GuiceApplication;
import com.cathive.fx.guice.GuiceFXMLLoader;
import com.google.inject.Module;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import nl.han.toetsplatform.module.voorbereiden.guice.GuiceModuleStub;

import javax.inject.Inject;
import java.util.List;

public class SamenstellenTentamenWindowStub extends GuiceApplication {
    @Inject
    private GuiceFXMLLoader fxmlLoader;

    @Override
    public void start(Stage primaryStage)  throws Exception{
        Parent root = fxmlLoader.load(getClass().getResource("/fxml/TentamenSamenstellen.fxml"), null).getRoot();
        primaryStage.setTitle("Hello Test");
        primaryStage.setScene(new Scene(root, 700, 500));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void init(List<Module> modules) throws Exception {
        modules.add(new GuiceModuleStub());
    }
}