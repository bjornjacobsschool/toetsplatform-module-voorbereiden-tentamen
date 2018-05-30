package nl.han.toetsplatform.module.voorbereiden.klaarzettententamen;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;

    /**
     * The data as an observable list of Persons.
     */
    private ObservableList<Tentamen> tentamenData = FXCollections.observableArrayList();

    public Main() {
        tentamenData.add(new Tentamen("SWA 1", "pompoen", "Eerste SWA toets"));
        tentamenData.add(new Tentamen("SWA 2", "Monkey", "Tweede SWA toets"));
        tentamenData.add(new Tentamen("SWA 3", "Test", "Derde SWA toets"));
    }

    /**
     * Returns the data as an observable list of Persons.
     * @return
     */
    public ObservableList<Tentamen> getTentamenData() {
        return tentamenData;
    }


    @Override
    public void start(Stage primaryStage) throws Exception{

        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Docent app");

        initRootLayour();
        showTentamenOverview();
    }

    public void initRootLayour() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/fxml/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout, 500, 400);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean showTentamenKlaarzettenDialog(Tentamen tentamen) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/fxml/TentamenKlaarzetten.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            //create the dialog stage
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Tentamen klaarzetten");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page, 400, 300);
            dialogStage.setScene(scene);

            // Set the tentamen into the controller.
            TentamenKlaarzettenController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setTentamen(tentamen);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    private void showTentamenOverview() {
        try {
            // Load tentamen overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/fxml/TentamenKlaarzettenOverview.fxml"));
            AnchorPane tentamenOverview = (AnchorPane) loader.load();

            // Set tentamen overview into the center of root layout.
            rootLayout.setCenter(tentamenOverview);

            // Give the controller access to the main app.
            TentamenOverviewController controller = loader.getController();
            controller.setMainApp(this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }
    public static void main(String[] args) {
        launch(args);
    }



}
