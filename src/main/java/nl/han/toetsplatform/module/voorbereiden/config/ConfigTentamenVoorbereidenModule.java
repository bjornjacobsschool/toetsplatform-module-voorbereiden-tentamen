package nl.han.toetsplatform.module.voorbereiden.config;

import java.net.URL;

public class ConfigTentamenVoorbereidenModule {
    public static URL getFXMLTentamenUitvoeren(SamenstellenTentamenFXMLFiles fxmlFile) {
        return ConfigTentamenVoorbereidenModule.class.getResource("/fxml/"+fxmlFile+".fxml");
    }
}
