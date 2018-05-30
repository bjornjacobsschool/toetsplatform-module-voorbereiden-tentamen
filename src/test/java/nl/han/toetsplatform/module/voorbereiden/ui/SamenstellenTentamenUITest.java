package nl.han.toetsplatform.module.voorbereiden.ui;

import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.control.LabeledMatchers.*;
import nl.han.toetsplatform.module.voorbereiden.ui.windows.SamenstellenTentamenWindowStub;
import org.junit.Before;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;

import org.junit.Test;

public class SamenstellenTentamenUITest extends ApplicationTest {

    @Before
    public void setup() throws Exception {
        SamenstellenTentamenWindowStub maintest = new SamenstellenTentamenWindowStub();
        FxToolkit.registerPrimaryStage();
        FxToolkit.setupApplication(maintest.getClass());
    }

    @Test public void should_contain_button() {
        // expect:
        verifyThat("#vraagToevoegenButton", hasText("Vraag toevoegen"));
    }

    @Test public void should_click_on_button() {
        // when:
        clickOn("#vraagToevoegenButton");

        // then:
        verifyThat("#toegevoegdeLabel", hasText("DI werkt"));
    }
}