package nl.han.toetsplatform.module.voorbereiden.klaarzettententamen;

public class TentamenCell extends javafx.scene.control.ListCell<Tentamen> {

    @Override
    protected void updateItem(Tentamen item, boolean empty) {
        super.updateItem(item, empty);

        int index = this.getIndex();
        String name = null;

        //format name
        if(item==null || empty) {

        } else {
            name = (index + 1 ) + ". " +
                    item.getNaam() + ", " +
                    "Key: " + item.getSleutel();
        }

        this.setText(name);
        setGraphic(null);
    }
}
