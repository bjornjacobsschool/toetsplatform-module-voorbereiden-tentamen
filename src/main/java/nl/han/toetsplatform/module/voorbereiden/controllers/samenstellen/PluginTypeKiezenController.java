package nl.han.toetsplatform.module.voorbereiden.controllers.samenstellen;

import javafx.event.ActionEvent;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import nl.han.toetsplatform.module.shared.plugin.PluginLoader;

import java.util.List;

public class PluginTypeKiezenController {
    public ComboBox pluginsComboBox;

    Stage dialogStage;

    String selectedPlugin;


    public void initialize(){
       List<Class> plugins = PluginLoader.getPlugins();
       for(Class c : plugins){
           pluginsComboBox.getItems().add(new PluginItem(c.getName()));
       }

       pluginsComboBox.getSelectionModel().select(0);
    }

    public void btnSelecteerPressed(ActionEvent actionEvent) {
        PluginItem item = (PluginItem)pluginsComboBox.getSelectionModel().getSelectedItem();
        selectedPlugin = item.getFullPath();
        dialogStage.close();
    }

    public String getSelectedPlugin() {
        return selectedPlugin;
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void btnAnnulerenPressed(ActionEvent actionEvent) {
        dialogStage.close();

    }

    private class PluginItem{
        private String fullPath;

        public PluginItem(String fullPath) {
            this.fullPath = fullPath;
        }

        @Override
        public String toString() {
            int d = fullPath.lastIndexOf(".") + 1;
            return fullPath.substring(d, fullPath.length());
        }

        public String getFullPath() {
            return fullPath;
        }

        public void setFullPath(String fullPath) {
            this.fullPath = fullPath;
        }
    }
}
