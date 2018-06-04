package nl.han.toetsplatform.module.voorbereiden.ui;

import nl.han.toetsplatform.module.voorbereiden.ui.windows.TentamenOverzichtWindowStub;
import org.junit.Before;
import org.junit.Test;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;

public class TentamenOverzichtUITest extends ApplicationTest {

    @Before
    public void setup() throws Exception {
        TentamenOverzichtWindowStub maintest = new TentamenOverzichtWindowStub();
        FxToolkit.registerPrimaryStage();
        FxToolkit.setupApplication(maintest.getClass());
    }

    @Test
    public void klaarZettenTentamenButtonNeedsSelectedTentamen(){
        clickOn("#klaarzettenButton");
        //verifyThat("#klaarzettenButton", hasText("Vraag toevoegen"));
    }
}
