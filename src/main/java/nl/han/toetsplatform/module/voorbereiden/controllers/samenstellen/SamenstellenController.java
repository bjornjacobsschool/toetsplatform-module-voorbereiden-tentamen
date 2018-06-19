package nl.han.toetsplatform.module.voorbereiden.controllers.samenstellen;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import nl.han.toetsapplicatie.apimodels.dto.SamengesteldTentamenDto;
import nl.han.toetsapplicatie.apimodels.dto.VragenbankVraagDto;
import nl.han.toetsplatform.module.voorbereiden.data.VragenDao;
import nl.han.toetsplatform.module.voorbereiden.util.EditingCell;

import javax.inject.Inject;
import java.util.List;
import java.util.function.Consumer;

import static nl.han.toetsplatform.module.voorbereiden.util.RunnableUtil.runIfNotNull;

public class SamenstellenController {
    public TableView<VragenbankVraagDto> vragenTable;
    public TableColumn<VragenbankVraagDto, String> vraagColumn;
    public TableColumn<VragenbankVraagDto, Integer> puntenColumn;
    public VBox vragenVbox;
    private ObservableList<VragenbankVraagDto> tableData = FXCollections.observableArrayList();
    private SamengesteldTentamenDto tentamen;

    private Runnable vraagToevoegen;
    private Consumer<SamengesteldTentamenDto> onTentamenOpslaan;
    private Runnable onAnnuleren;

    @Inject
    private VragenDao _vragenDao;

    public void setOnTentamenOpslaan(Consumer<SamengesteldTentamenDto> onTentamenOpslaan) {
        this.onTentamenOpslaan = onTentamenOpslaan;
    }

    public void setVraagToevoegen(Runnable vraagToevoegen) {
        this.vraagToevoegen = vraagToevoegen;
    }

    public void setOnAnnuleren(Runnable onAnnuleren) { this.onAnnuleren = onAnnuleren; }

    @FXML
    protected void initialize() {
        vraagColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNaam()));
        puntenColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<Integer>(cellData.getValue().getPunten()));
        Callback<TableColumn<VragenbankVraagDto, Integer>, TableCell<VragenbankVraagDto, Integer>> cellFactory
                = (TableColumn<VragenbankVraagDto, Integer> param) -> new EditingCell();
        puntenColumn.setCellFactory(cellFactory);
        puntenColumn.setOnEditCommit(
                (TableColumn.CellEditEvent<VragenbankVraagDto, Integer> t) -> {
                    ((VragenbankVraagDto) t.getTableView().getItems()
                            .get(t.getTablePosition().getRow()))
                            .setPunten(t.getNewValue());

                });
    }

    public void setVragen(List<VragenbankVraagDto> vragen){
        vragenVbox.getChildren().clear();

        for(VragenbankVraagDto vraag : vragen){
            HBox hBox = new HBox();
            Label lblNaam = new Label(vraag.getNaam());
            Button btnAdd = new Button("Add");
            btnAdd.setOnAction( (e) -> {
                voegVraagToe(vraag);
            });
            hBox.getChildren().add(lblNaam);
            hBox.getChildren().add(btnAdd);
            vragenVbox.getChildren().add(hBox);
        }
    }

    @FXML
    protected void handleVraagToevoegenButtonAction(ActionEvent event) {
        runIfNotNull(vraagToevoegen);
    }

    public void voegVraagToe(VragenbankVraagDto vraag){
        tableData.add(vraag);
        vragenTable.setItems(tableData);

    }

    @FXML
    protected void handleAnnulerenButtonAction(ActionEvent event) {
        runIfNotNull(onAnnuleren);
    }

    @FXML
    public void handleTentamenOpslaanButtonAction(ActionEvent event) {
        int index = 0;
        for(VragenbankVraagDto v: tableData){
            vragenTable.getItems().get(index).setPunten(puntenColumn.getCellData(index));
            index++;
        }

        tentamen.setVragen(tableData);
        runIfNotNull(onTentamenOpslaan, tentamen);
        // actie voor voorblad aanmaken
    }

    public void setTentamen(SamengesteldTentamenDto tentamen) {
        this.tentamen = tentamen;
    }
}
