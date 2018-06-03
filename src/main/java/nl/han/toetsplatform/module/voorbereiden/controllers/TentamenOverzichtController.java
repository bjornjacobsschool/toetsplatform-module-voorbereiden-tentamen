package nl.han.toetsplatform.module.voorbereiden.controllers;

import com.cathive.fx.guice.GuiceFXMLLoader;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import nl.han.toetsplatform.module.voorbereiden.Main;
import nl.han.toetsplatform.module.voorbereiden.applicationlayer.ITentamenKlaarzetten;
import nl.han.toetsplatform.module.voorbereiden.config.ConfigTentamenVoorbereidenModule;
import nl.han.toetsplatform.module.voorbereiden.config.PrimaryStageConfig;
import nl.han.toetsplatform.module.voorbereiden.config.TentamenVoorbereidenFXMLFiles;
import nl.han.toetsplatform.module.voorbereiden.controllers.klaarzetten.KlaarzettenController;
import nl.han.toetsplatform.module.voorbereiden.models.KlaargezetTentamen;
import nl.han.toetsplatform.module.voorbereiden.models.Tentamen;

import javax.inject.Inject;
import java.io.IOException;

public class TentamenOverzichtController {

    public AnchorPane mainContainer;
    GuiceFXMLLoader fxmlLoader;
    GuiceFXMLLoader.Result samenstellenView;
    private ITentamenKlaarzetten _ITentamenKlaarzetten;

    @FXML
    private TableView<Tentamen> tentamenTable;
    @FXML
    private TableColumn<Tentamen, String> nameColumn;
    @FXML
    private TableColumn<Tentamen, String> descriptionColumn;

    @FXML
    private Label NaamLabel;
    @FXML
    private Label DescriptionLabel;

    /**
     * The data as an observable list of Persons.
     */
    private ObservableList<Tentamen> tentamenData = FXCollections.observableArrayList();

    @Inject
    public TentamenOverzichtController(GuiceFXMLLoader fxmlLoader, ITentamenKlaarzetten _ITentamenKlaarzetten) {
        this.fxmlLoader = fxmlLoader;
        this._ITentamenKlaarzetten = _ITentamenKlaarzetten;
    }

    @FXML
    public void initialize() throws IOException {
        refreshOverzicht();

        // Initialize the tentamen table with the two columns.
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNaam()));

        // Clear tentamen details.
        showTentamenDetails(null);

        // Listen for selection changes and show the tentamen details when changed.
        tentamenTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showTentamenDetails(newValue));
    }

    /**
     * Fills all text fields to show details about the tentamen.
     * If the specified tentamen is null, all text fields are cleared.
     *
     * @param tentamen the tentamen or null
     */
    private void showTentamenDetails(Tentamen tentamen) {
        if (tentamen != null) {
            // Fill the labels with info from the tentamen object.
            NaamLabel.setText(tentamen.getNaam());
            DescriptionLabel.setText(tentamen.getBeschrijving());
        } else {
            // Tentamen is null, remove all the text.
            NaamLabel.setText("");
            DescriptionLabel.setText("");
        }
    }


    /**
     * Called when the user clicks the 'Klaarzetten' button.
     * @param actionEvent
     */
    public void handleKlaarzettenTentamen(ActionEvent actionEvent) {
        Tentamen selectedItem = tentamenTable.getSelectionModel().getSelectedItem();

        if(selectedItem != null) {
            boolean okClicked = showTentamenKlaarzettenDialog(selectedItem);
            if(okClicked) {
                showTentamenDetails(selectedItem);
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(PrimaryStageConfig.getInstance().getPrimaryStage());
            alert.setTitle("No selection");
            alert.setHeaderText("No tentamen selected");
            alert.setContentText("Please select a tentamen from the list");

            alert.showAndWait();
        }
    }

    public void refreshOverzicht() {
        tentamenData.addAll(this._ITentamenKlaarzetten.getTentamens());
        tentamenTable.setItems(tentamenData);
    }

    public boolean showTentamenKlaarzettenDialog(Tentamen tentamen) {
        try {
            GuiceFXMLLoader.Result klaarzettenView = fxmlLoader.load(Main.class.getResource("/fxml/TentamenKlaarzetten.fxml"));

            //create the dialog stage
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Tentamen klaarzetten");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(PrimaryStageConfig.getInstance().getPrimaryStage());
            Scene scene = new Scene(klaarzettenView.getRoot(), 400, 300);
            dialogStage.setScene(scene);

            // Set the tentamen into the controller.
            KlaarzettenController controller = klaarzettenView.getController();
            controller.setOnKlaarzettenTentamen(this::onTentamenKlaargezet);
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

    public void onTentamenKlaargezet(KlaargezetTentamen klaargezetTentamen) {
        //write to db
        System.out.println(klaargezetTentamen.getSleutel());
    }

    public void handleNewTentamen(ActionEvent actionEvent) throws IOException {
        samenstellenView = fxmlLoader.load(ConfigTentamenVoorbereidenModule.getFXMLTentamenVoorbereiden(TentamenVoorbereidenFXMLFiles.SamenstellenMain), null);
        mainContainer.getChildren().clear();
        mainContainer.getChildren().add(samenstellenView.getRoot());
    }

}

