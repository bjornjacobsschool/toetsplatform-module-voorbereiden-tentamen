package nl.han.toetsplatform.module.voorbereiden.controllers;

import com.cathive.fx.guice.GuiceFXMLLoader;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import nl.han.toetsplatform.module.shared.storage.StorageDao;
import nl.han.toetsplatform.module.voorbereiden.Main;
import nl.han.toetsplatform.module.voorbereiden.applicationlayer.ITentamenKlaarzetten;
import nl.han.toetsplatform.module.voorbereiden.config.ConfigTentamenVoorbereidenModule;
import nl.han.toetsplatform.module.voorbereiden.config.PrimaryStageConfig;
import nl.han.toetsplatform.module.voorbereiden.config.TentamenVoorbereidenFXMLFiles;
import nl.han.toetsplatform.module.voorbereiden.controllers.klaarzetten.KlaarzettenController;
import nl.han.toetsplatform.module.voorbereiden.data.SqlLoader;
import nl.han.toetsplatform.module.voorbereiden.exceptions.GatewayCommunicationException;
import nl.han.toetsplatform.module.voorbereiden.models.KlaargezetTentamen;
import nl.han.toetsplatform.module.voorbereiden.models.Tentamen;
import nl.han.toetsplatform.module.voorbereiden.util.TentamenFile;

import javax.inject.Inject;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TentamenOverzichtController {


    private final static Logger LOGGER = Logger.getLogger(TentamenOverzichtController.class.getName());

    private final TentamenFile _tentamenFile;
    public AnchorPane mainContainer;
    GuiceFXMLLoader fxmlLoader;
    GuiceFXMLLoader.Result samenstellenView;
    private ITentamenKlaarzetten _tentamenKlaarzetten;

    @FXML
    private TableView<Tentamen> tentamenTable;
    @FXML
    private TableColumn<Tentamen, String> nameColumn;
    @FXML
    private TableColumn<Tentamen, String> vakColumn;

    @FXML
    private Label naamLabel;
    @FXML
    private Label descriptionLabel;
    @FXML
    public Label vakLabel;
    @FXML
    public Label hulpmiddelenLabel;
    @FXML
    public Label versieLabel;
    @FXML
    public Label tijdsduurLabel;

    @Inject
    private StorageDao storageDao;

    @Inject
    private SqlLoader sqlLoader;


    /**
     * The data as an observable list of Persons.
     */
    private ObservableList<Tentamen> tentamenData = FXCollections.observableArrayList();
    private Stage klaarzettenPopupStage;

    @Inject
    public TentamenOverzichtController(GuiceFXMLLoader fxmlLoader, ITentamenKlaarzetten _tentamenKlaarzetten, TentamenFile tentamenFile) {
        this.fxmlLoader = fxmlLoader;
        this._tentamenKlaarzetten = _tentamenKlaarzetten;
        this._tentamenFile = tentamenFile;
    }

    @FXML
    public void initialize() throws IOException {
        //Create the database
        try {
            storageDao.executeUpdate(sqlLoader.load("DDL"));
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Could not create database: " + e.getMessage());
        }

        refreshOverzicht();

        // Initialize the tentamen table with the two columns.
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNaam()));
        vakColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getVak()));

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
            naamLabel.setText(tentamen.getNaam());
            descriptionLabel.setText(tentamen.getBeschrijving());
            hulpmiddelenLabel.setText(tentamen.getToegestaandeHulpmiddelen());
            vakLabel.setText(tentamen.getVak());
            tijdsduurLabel.setText(String.valueOf(tentamen.getTijdsduur()));
            versieLabel.setText(tentamen.getVersie().getNumber());

        } else {
            // Tentamen is null, remove all the text.
            naamLabel.setText("");
            descriptionLabel.setText("");
            hulpmiddelenLabel.setText("");
            vakLabel.setText("");
            tijdsduurLabel.setText("");
            versieLabel.setText("");
        }
    }

    /**
     * Called when the user clicks the 'Klaarzetten' button. It first checks if an item is selected. If there isn't
     * it shows a warning alert dialog else it shows a new dialog for "het klaarzetten".
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

    /**
     * Refresh the data for the tentamenTable.
     */
    public void refreshOverzicht() {
        tentamenData.addAll(this._tentamenKlaarzetten.getTentamens());
        tentamenTable.setItems(tentamenData);
    }

    /**
     * Shows a popup window with the selected Tentamen object.
     * @param tentamen
     * @return
     */
    public boolean showTentamenKlaarzettenDialog(Tentamen tentamen) {
        try {
            GuiceFXMLLoader.Result klaarzettenView = fxmlLoader.load(Main.class.getResource("/fxml/TentamenKlaarzetten.fxml"));

            //create the dialog stage
            klaarzettenPopupStage = new Stage();
            klaarzettenPopupStage.setTitle("Tentamen klaarzetten");
            klaarzettenPopupStage.initModality(Modality.WINDOW_MODAL);
            klaarzettenPopupStage.initOwner(PrimaryStageConfig.getInstance().getPrimaryStage());
            Scene scene = new Scene(klaarzettenView.getRoot(), 400, 300);
            klaarzettenPopupStage.setScene(scene);

            // Set the tentamen into the controller.
            KlaarzettenController controller = klaarzettenView.getController();
            controller.setOnKlaarzettenTentamen(this::onTentamenKlaargezet);
            controller.setDialogStage(klaarzettenPopupStage);
            controller.setTentamen(tentamen);

            // Show the dialog and wait until the user closes it
            klaarzettenPopupStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Method that should save the given object to the database.
     * For now this will save a JSON file in the directory you choose.
     * @param klaargezetTentamen
     */
    public void onTentamenKlaargezet(KlaargezetTentamen klaargezetTentamen) {
        try {
            _tentamenFile.ExportToFile(klaargezetTentamen);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Could not create tentamen file");
        }
        //write to db
        try {
            _tentamenKlaarzetten.opslaan(klaargezetTentamen);
        } catch (GatewayCommunicationException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * When clicked on "new..." Load the samenstellen view.
     * @param actionEvent
     * @throws IOException
     */
    public void handleNewTentamen(ActionEvent actionEvent) throws IOException {
        samenstellenView = fxmlLoader.load(ConfigTentamenVoorbereidenModule.getFXMLTentamenVoorbereiden(TentamenVoorbereidenFXMLFiles.SamenstellenMain), null);
        setAnchorFull(samenstellenView.getRoot());
        mainContainer.getChildren().clear();
        mainContainer.getChildren().add(samenstellenView.getRoot());
    }


    private void setAnchorFull(Node node){
        AnchorPane.setBottomAnchor(node, 0D);
        AnchorPane.setLeftAnchor(node, 0D);
        AnchorPane.setRightAnchor(node, 0D);
        AnchorPane.setTopAnchor(node, 0D);
    }

}

