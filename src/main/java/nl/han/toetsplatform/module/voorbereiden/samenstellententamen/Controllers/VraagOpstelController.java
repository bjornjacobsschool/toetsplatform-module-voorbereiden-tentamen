package nl.han.toetsplatform.module.voorbereiden.samenstellententamen.Controllers;

import javafx.event.ActionEvent;

import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import nl.han.toetsplatform.module.voorbereiden.samenstellententamen.data.VraagOpslaanDAO;
import nl.han.toetsapplicatie.module.model.Vraag;
import nl.han.toetsapplicatie.module.plugin.Plugin;
import nl.han.toetsapplicatie.module.plugin.PluginLoader;

import java.util.UUID;


public class VraagOpstelController {

    public TextField lblVraagName;
    public AnchorPane opstelContainer;
    Vraag vraag;
    Plugin plugin;
    VraagOpslaanDAO vraagOpslaan;

    Runnable onAnnuleer;

    public void setVraagOpslaan(VraagOpslaanDAO vraagOpslaan){
        this.vraagOpslaan = vraagOpslaan;
    }

    public void setOnExit(Runnable onExit) {
        this.onAnnuleer = onExit;
    }

    public void setVraag(Vraag vraag) {
        this.vraag = vraag;
        try {
            plugin = PluginLoader.getPlugin(vraag);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        opstelContainer.getChildren().add(plugin.getVraagCreatorView().getView());
    }

    public void btnAnnuleerPressed(ActionEvent event) {
        if (onAnnuleer != null)
            onAnnuleer.run();

    }

    public void btnOpslaanPressed(ActionEvent event){
      vraag.setData(plugin.getVraagCreatorView().getQuestionData());
      UUID uuid = UUID.randomUUID();
      String randomUUIDString = uuid.toString();
        //Deze kan straks weg. Is nu puur voor testdoeleinden.
        //Wordt straks vraag.setId(randomUUIDString);
        System.out.println(randomUUIDString);
      vraag.setId(1);
      vraagOpslaan.nieuweVraagOpslaan(vraag);
      if(onAnnuleer != null)
          onAnnuleer.run();


    }
}
