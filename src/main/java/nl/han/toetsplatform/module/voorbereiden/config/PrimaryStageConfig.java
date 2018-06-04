package nl.han.toetsplatform.module.voorbereiden.config;

import javafx.stage.Stage;

public class PrimaryStageConfig {
    private static PrimaryStageConfig instance = null;
    private Stage _primaryStage;
    public PrimaryStageConfig() {

    }

    public static PrimaryStageConfig getInstance() {
        if(instance == null) {
            instance = new PrimaryStageConfig();
        }
        return instance;
    }

    public void setPrimaryStage(Stage primaryStage)
    {
        this._primaryStage = primaryStage;
    }

    public Stage getPrimaryStage() {
        return this._primaryStage;
    }
}
