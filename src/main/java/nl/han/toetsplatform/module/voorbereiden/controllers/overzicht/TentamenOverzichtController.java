package nl.han.toetsplatform.module.voorbereiden.controllers.overzicht;

import com.cathive.fx.guice.GuiceFXMLLoader;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import nl.han.toetsapplicatie.apimodels.dto.SamengesteldTentamenDto;
import nl.han.toetsapplicatie.apimodels.dto.KlaargezetTentamenDto;
import nl.han.toetsplatform.module.voorbereiden.applicationlayer.ITentamenKlaarzetten;
import nl.han.toetsplatform.module.voorbereiden.config.PrimaryStageConfig;
import nl.han.toetsplatform.module.voorbereiden.data.sql.SqlDataBaseCreator;
import nl.han.toetsplatform.module.voorbereiden.exceptions.GatewayCommunicationException;
import nl.han.toetsplatform.module.voorbereiden.util.TentamenFile;

import javax.inject.Inject;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import static nl.han.toetsplatform.module.voorbereiden.util.RunnableUtil.runIfNotNull;

public class TentamenOverzichtController {

    private final static Logger LOGGER = Logger.getLogger(TentamenOverzichtController.class.getName());
    private ITentamenKlaarzetten _tentamenKlaarzetten;

    private TentamenFile _tentamenFile;
    private GuiceFXMLLoader fxmlLoader;
    public AnchorPane mainContainer;

    private ObservableList<SamengesteldTentamenDto> tentamenData = FXCollections.observableArrayList();
    @FXML
    private TableView<SamengesteldTentamenDto> tentamenTable;
    @FXML
    private TableColumn<SamengesteldTentamenDto, String> nameColumn;
    @FXML
    private TableColumn<SamengesteldTentamenDto, String> vakColumn;

    private ObservableList<KlaargezetTentamenDto> klaargezetteData = FXCollections.observableArrayList();
    @FXML
    private TableView<KlaargezetTentamenDto> klaargezetteTentamenTable;
    @FXML
    private TableColumn<KlaargezetTentamenDto, String> klaargezetNaamColumn;
    @FXML
    private TableColumn<KlaargezetTentamenDto, String> startDateColumn;

    @FXML
    private Label naamLabel;
    @FXML
    private Label descriptionLabel;
    @FXML
    private Label vakLabel;
    @FXML
    private Label hulpmiddelenLabel;
    @FXML
    private Label versieLabel;
    @FXML
    private Label tijdsduurLabel;

    @FXML
    private Label startDatumLabel;
    @FXML
    private Label eindDatumKlaargezetTentamenLabel;
    @FXML
    private Label sleutelLabel;

    @Inject
    SqlDataBaseCreator dataBaseCreator;


    /**
     * The data as an observable list of Persons.
     */
   // private ObservableList<SamengesteldTentamenDto> tentamenData = FXCollections.observableArrayList();
    private Stage klaarzettenPopupStage;
    private Runnable onNieuwTentamen;

    @Inject
    public TentamenOverzichtController(GuiceFXMLLoader fxmlLoader, ITentamenKlaarzetten _tentamenKlaarzetten, TentamenFile tentamenFile) {
        this.fxmlLoader = fxmlLoader;
        this._tentamenKlaarzetten = _tentamenKlaarzetten;
        this._tentamenFile = tentamenFile;
    }

    @FXML
    public void initialize() {
        dataBaseCreator.create();

        refreshOverzicht();

        // Initialize the tentamen table with the two columns.
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNaam()));
        vakColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getVak()));

        //Initialize the klaargezette table with the two columsn.
        klaargezetNaamColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNaam()));
        startDateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(timestampToDate(cellData.getValue().getStartdatum())));

        // Clear tentamen details.
        showTentamenDetails(null);
        showKlaargezetteTentamenDetails(null);

        // Listen for selection changes and show the tentamen details when changed.
        tentamenTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showTentamenDetails(newValue));
        klaargezetteTentamenTable.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> showKlaargezetteTentamenDetails(newValue)));
    }

    public String timestampToDate(long timestamp) {
        Date date = new Date(timestamp);
        return String.valueOf(date);
    }

    /**
     * Fills all text fields to show details about the tentamen.
     * If the specified tentamen is null, all text fields are cleared.
     *
     * @param tentamen the tentamen or null
     */
    private void showTentamenDetails(SamengesteldTentamenDto tentamen) {
        setEmptyStrings();

        if (tentamen != null) {
            // Fill the labels with info from the tentamen object.
            naamLabel.setText(tentamen.getNaam());
            descriptionLabel.setText(tentamen.getBeschrijving());
            hulpmiddelenLabel.setText(tentamen.getToegestaandeHulpmiddelen());
            vakLabel.setText(tentamen.getVak());
            tijdsduurLabel.setText(String.valueOf(tentamen.getTijdsduur()));
            versieLabel.setText(String.valueOf(tentamen.getVersie().getNummer()));

        }
    }

    /**
     * Fills all text fields to show details about the tentamen.
     * If the specified tentamen is null, all text fields are cleared.
     *
     * @param tentamen the tentamen or null
     */
    private void showKlaargezetteTentamenDetails(KlaargezetTentamenDto tentamen) {
        setEmptyStrings();

        if (tentamen != null) {
            // Fill the labels with info from the tentamen object.
            naamLabel.setText(tentamen.getNaam());
            descriptionLabel.setText(tentamen.getBeschrijving());
            hulpmiddelenLabel.setText(tentamen.getToegestaandeHulpmiddelen());
            tijdsduurLabel.setText(String.valueOf(tentamen.getTijdsduur()));
            versieLabel.setText(String.valueOf(tentamen.getVersie().getNummer()));
            startDatumLabel.setText(timestampToDate(tentamen.getStartdatum()));

        }
    }

    /**
     * Methode om de labels te legen
     */
    private void setEmptyStrings() {
        naamLabel.setText("");
        descriptionLabel.setText("");
        hulpmiddelenLabel.setText("");
        vakLabel.setText("");
        tijdsduurLabel.setText("");
        versieLabel.setText("");
        startDatumLabel.setText("");

    }

    /**
     * Called when the user clicks the 'Klaarzetten' button. It first checks if an item is selected. If there isn't
     * it shows a warning alert dialog else it shows a new dialog for "het klaarzetten".
     *
     * @param actionEvent
     */
    public void handleKlaarzettenTentamen(ActionEvent actionEvent) {
        SamengesteldTentamenDto selectedItem = tentamenTable.getSelectionModel().getSelectedItem();

        if (selectedItem != null) {
             zetTentamenKlaarzettenDialog(selectedItem);
             showTentamenDetails(selectedItem);
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
        tentamenData.clear();
        tentamenData.addAll(this._tentamenKlaarzetten.getTentamens());
        tentamenTable.setItems(tentamenData);

        klaargezetteData.clear();
        klaargezetteData.addAll(this._tentamenKlaarzetten.getKlaargezetteTentamens());
        klaargezetteTentamenTable.setItems(klaargezetteData);
    }

    /**
     * Shows a popup window with the selected Tentamen object.
     *
     * @param tentamen
     * @return
     */
    public void zetTentamenKlaarzettenDialog(SamengesteldTentamenDto tentamen) {
        KlaargezetTentamenDto klaargezetTentamenDto = new KlaargezetTentamenDto();
        klaargezetTentamenDto.setId(tentamen.getId());
        klaargezetTentamenDto.setNaam(tentamen.getNaam());
        klaargezetTentamenDto.setBeschrijving(tentamen.getBeschrijving());
        klaargezetTentamenDto.setToegestaandeHulpmiddelen(tentamen.getToegestaandeHulpmiddelen());
        klaargezetTentamenDto.setTijdsduur(tentamen.getTijdsduur());
        klaargezetTentamenDto.setStartdatum(System.currentTimeMillis());
        klaargezetTentamenDto.setVragen(String.valueOf(tentamen.getVragen()));
        klaargezetTentamenDto.setVersie(tentamen.getVersie());

        try {
            _tentamenKlaarzetten.opslaan(klaargezetTentamenDto);
        } catch (GatewayCommunicationException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Alert alert = new Alert(Alert.AlertType.NONE, "Tentamen succesvol klaargezet", ButtonType.OK);
        alert.initOwner(this.naamLabel.getScene().getWindow());
        alert.showAndWait();

        refreshOverzicht();
    }

    /**
     * Method that should save the given object to the database.
     * For now this will save a JSON file in the directory you choose.
     *
     * @param klaargezetTentamen
     *
     */
    @Deprecated
    public void onTentamenKlaargezet(KlaargezetTentamenDto klaargezetTentamen) {
        try {
            _tentamenFile.ExportToFile(klaargezetTentamen);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Could not create tentamen file");
        }
        //write to db
        try {
            _tentamenKlaarzetten.opslaan(klaargezetTentamen);
        } catch (GatewayCommunicationException e) {
            LOGGER.log(Level.SEVERE, "GatewayCommunicatie exceptie.");
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "SQL Exception");
        }
    }

    /**
     * When clicked on "nieuw" Load the samenstellen view.
     *
     * @param actionEvent
     * @throws IOException
     */
    public void handleNewTentamen(ActionEvent actionEvent) throws IOException {
        runIfNotNull(onNieuwTentamen);
    }

    /**
     * Setter
     *
     * @param onNieuwTentamen
     */
    public void setOnNieuwTentamen(Runnable onNieuwTentamen) {
        this.onNieuwTentamen = onNieuwTentamen;
    }

    public void keyReleased(KeyEvent keyEvent) {
        if(keyEvent.getCode() == KeyCode.F5){
            refreshOverzicht();
        }
    }
}

