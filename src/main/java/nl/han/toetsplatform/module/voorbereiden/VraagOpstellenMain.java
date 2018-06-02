package nl.han.toetsplatform.module.voorbereiden;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import nl.han.toetsplatform.module.voorbereiden.controllers.VraagOpstelController;
import nl.han.toetsplatform.module.voorbereiden.data.VraagOpslaanDAO;
import nl.han.toetsapplicatie.module.model.Vraag;

import java.io.IOException;

public class VraagOpstellenMain extends Application {

    public static void main(String[] args) {
        launch(args);
    }
    Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Vraag vraag = new Vraag();
        vraag.setPlugin("nl.han.toetsapplicatie.plugin.GraphPlugin");
        vraag.setData("\"{\\\"nodes\\\":[{\\\"name\\\":\\\"B\\\",\\\"connectedNodes\\\":[{\\\"nodeInfo\\\":\\\"D\\\",\\\"distance\\\":2}]},{\\\"name\\\":\\\"A\\\",\\\"connectedNodes\\\":[{\\\"nodeInfo\\\":\\\"B\\\",\\\"distance\\\":33},{\\\"nodeInfo\\\":\\\"B\\\",\\\"distance\\\":33}]},{\\\"name\\\":\\\"D\\\",\\\"connectedNodes\\\":[]}],\\\"vraagText\\\":\\\"Hallo\\\"}\\n\"");

        vraag.setId(1);
        vraag.setName("test");
        this.primaryStage = primaryStage;
        Scene scene = new Scene(new Label("If you see this something is wrong"), 1024, 700);
        primaryStage.setScene(scene);
        primaryStage.setTitle("HAN - Student Applicatie");
        primaryStage.show();
        loadVraagEdit(new VraagOpslaanDAO() {
            public void nieuweVraagOpslaan(String vraag) {
                System.out.println(vraag);
            }
        }, vraag);
    }


    public Node loadVraagEdit(VraagOpslaanDAO vraagOpslaan, Vraag vraag){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/OpstellenVraag.fxml"));
            Parent toetsMakeParent = loader.load();
            VraagOpstelController controller = loader.getController();
            //controller.setVraagOpslaan(vraagOpslaan);
            //controller.setVraag(vraag);
            primaryStage.getScene().setRoot(toetsMakeParent);
            return toetsMakeParent;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
