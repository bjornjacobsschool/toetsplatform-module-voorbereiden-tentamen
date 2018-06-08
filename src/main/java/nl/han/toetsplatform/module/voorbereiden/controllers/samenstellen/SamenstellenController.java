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
import nl.han.toetsplatform.module.voorbereiden.data.VragenDao;
import nl.han.toetsplatform.module.voorbereiden.models.Tentamen;
import nl.han.toetsplatform.module.voorbereiden.models.Vraag;
import nl.han.toetsplatform.module.voorbereiden.util.EditingCell;

import javax.inject.Inject;
import java.util.List;
import java.util.function.Consumer;

import static nl.han.toetsplatform.module.voorbereiden.util.RunnableUtil.runIfNotNull;

public class SamenstellenController {
    public TableView<Vraag> vragenTable;
    public TableColumn<Vraag, String> vraagColumn;
    public TableColumn<Vraag, Integer> puntenColumn;
    public VBox vragenVbox;
    private ObservableList<Vraag> tableData = FXCollections.observableArrayList();
    private Tentamen tentamen;

    private Runnable vraagToevoegen;
    private Consumer<Tentamen> onTentamenOpslaan;
    private Runnable onAnnuleren;

    @Inject
    private VragenDao _vragenDao;

    public void setOnTentamenOpslaan(Consumer<Tentamen> onTentamenOpslaan) {
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
        Callback<TableColumn<Vraag, Integer>, TableCell<Vraag, Integer>> cellFactory
                = (TableColumn<Vraag, Integer> param) -> new EditingCell();
        puntenColumn.setCellFactory(cellFactory);
        puntenColumn.setOnEditCommit(
                (TableColumn.CellEditEvent<Vraag, Integer> t) -> {
                    ((Vraag) t.getTableView().getItems()
                            .get(t.getTablePosition().getRow()))
                            .setPunten(t.getNewValue());

                });
    }

    public void setVragen(List<Vraag> vragen){
        vragenVbox.getChildren().clear();

        for(Vraag vraag : vragen){
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

    public void voegVraagToe(Vraag vraag){
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
        for(Vraag v: tableData){
            vragenTable.getItems().get(index).setPunten(puntenColumn.getCellData(index));
            index++;
        }

        tentamen.setVragenVraag(tableData);
        runIfNotNull(onTentamenOpslaan, tentamen);
        // actie voor voorblad aanmaken
    }

    public void setTentamen(Tentamen tentamen) {
        this.tentamen = tentamen;
    }
}
