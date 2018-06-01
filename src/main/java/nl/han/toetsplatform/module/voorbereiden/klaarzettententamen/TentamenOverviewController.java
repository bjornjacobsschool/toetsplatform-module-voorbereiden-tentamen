package nl.han.toetsplatform.module.voorbereiden.klaarzettententamen;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class TentamenOverviewController {

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


    // Reference to the main application.
    private Main mainApp;


    @FXML
    public void initialize() {
        // Initialize the tentamen table with the two columns.
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().naamProperty());
        descriptionColumn.setCellValueFactory(cellData -> cellData.getValue().descProperty());

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
            DescriptionLabel.setText(tentamen.getDesc());
        } else {
            // Tentamen is null, remove all the text.
            NaamLabel.setText("");
            DescriptionLabel.setText("");
        }
    }

    /**
     * Is called by the main application to give a reference back to itself.
     *
     * @param mainApp
     */
    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;

        // Add observable list data to the table
        tentamenTable.setItems(mainApp.getTentamenData());
    }

    /**
     * Called when the user clicks the 'Klaarzetten' button.
     * @param actionEvent
     */
    public void handleKlaarzettenTentamen(ActionEvent actionEvent) {
        Tentamen selectedItem = tentamenTable.getSelectionModel().getSelectedItem();

        if(selectedItem != null) {
            boolean okClicked = mainApp.showTentamenKlaarzettenDialog(selectedItem);
            if(okClicked) {
                showTentamenDetails(selectedItem);
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("No selection");
            alert.setHeaderText("No tentamen selected");
            alert.setContentText("Please select a tentamen from the list");

            alert.showAndWait();
        }
    }
}

