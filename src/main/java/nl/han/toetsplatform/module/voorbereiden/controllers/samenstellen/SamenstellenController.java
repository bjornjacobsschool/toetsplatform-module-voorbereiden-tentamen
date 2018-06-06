package nl.han.toetsplatform.module.voorbereiden.controllers.samenstellen;

import com.cathive.fx.guice.GuiceFXMLLoader;
import com.google.gson.Gson;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;
import javafx.util.StringConverter;
import nl.han.toetsplatform.module.voorbereiden.models.Tentamen;
import nl.han.toetsplatform.module.voorbereiden.models.Vraag;
import nl.han.toetsplatform.module.voorbereiden.models.VraagTest;
import nl.han.toetsplatform.module.voorbereiden.util.EditingCell;

import java.util.function.Consumer;

import static nl.han.toetsplatform.module.voorbereiden.util.RunnableUtil.runIfNotNull;

public class SamenstellenController {
    public AnchorPane childPane;
    public GridPane vragenPane;
    public TableView<Vraag> vragenTable;
    public TableColumn<Vraag, String> vraagColumn;
    public TableColumn<Vraag, Integer> puntenColumn;
    private ObservableList<Vraag> tableData = FXCollections.observableArrayList();
    private Tentamen tentamen;

    private Runnable vraagToevoegen;
    private Consumer<Tentamen> onTentamenOpslaan;
    private Runnable onAnnuleren;

    public void setOnTentamenOpslaan(Consumer<Tentamen> onTentamenOpslaan) {
        this.onTentamenOpslaan = onTentamenOpslaan;
    }

    public void setVraagToevoegen(Runnable vraagToevoegen) {
        this.vraagToevoegen = vraagToevoegen;
    }

    public void setOnAnnuleren(Runnable onAnnuleren) {
        this.onAnnuleren = onAnnuleren;
    }

    @FXML
    protected void initialize() {
        vragenPane.setStyle("-fx-border-style: solid");
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

        // actie voor annuleren
    }

    @FXML
    public void handleTentamenOpslaanButtonAction(ActionEvent event) {
          int index = 0;
        for(Vraag v: tableData){
            vragenTable.getItems().get(index).setPunten(puntenColumn.getCellData(index));
            index++;
        }

        tentamen.setVragen(tableData);
        runIfNotNull(onTentamenOpslaan, tentamen);
        // actie voor voorblad aanmaken
    }

    public void setTentamen(Tentamen tentamen) {
        this.tentamen = tentamen;
    }
}
