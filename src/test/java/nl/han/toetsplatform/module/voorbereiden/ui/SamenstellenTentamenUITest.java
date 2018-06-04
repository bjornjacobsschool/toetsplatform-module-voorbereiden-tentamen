package nl.han.toetsplatform.module.voorbereiden.ui;

import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.control.LabeledMatchers.*;

import nl.han.toetsplatform.module.voorbereiden.config.TentamenVoorbereidenFXMLFiles;
import nl.han.toetsplatform.module.voorbereiden.ui.windows.SamenstellenTentamenWindowStub;
import org.junit.Before;
import org.junit.Test;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;

public class SamenstellenTentamenUITest extends ApplicationTest {

    @Before
    public void setup() throws Exception {
        SamenstellenTentamenWindowStub maintest = new SamenstellenTentamenWindowStub();
        FxToolkit.registerPrimaryStage();
        FxToolkit.setupApplication(maintest.getClass());
    }

    @Test
    public void vraagToevoegenButtonMoetDeTekstVraagToevoegenHebben(){
        verifyThat("#vraagToevoegenButton", hasText("Vraag toevoegen"));
    }

    @Test
    public void vraagToevoegenButtonClickDanLabelMetTekstDIWerkt() {
        clickOn("#annulerenButton");

    }
}