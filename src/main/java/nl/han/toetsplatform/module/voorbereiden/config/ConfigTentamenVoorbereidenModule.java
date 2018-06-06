package nl.han.toetsplatform.module.voorbereiden.config;

import com.google.inject.Module;
import nl.han.toetsplatform.module.voorbereiden.guice.GuiceModule;

import java.net.URL;

public class ConfigTentamenVoorbereidenModule {

    public static URL getFXMLTentamenVoorbereiden(TentamenVoorbereidenFXMLFiles fxmlFile) {
        return ConfigTentamenVoorbereidenModule.class.getResource("/fxml/"+fxmlFile+".fxml");
    }

    public static Module getModule() {
        return new GuiceModule();
    }
}
