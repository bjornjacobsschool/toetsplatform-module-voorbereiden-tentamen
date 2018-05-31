package nl.han.toetsplatform.module.voorbereiden.controllers;


import javafx.scene.layout.AnchorPane;
import nl.han.toetsplatform.module.voorbereiden.applicationlayer.IInterfaceOmTeDemostrerenDatDIWerkt;;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.testfx.framework.junit.ApplicationTest;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SamenstellenControllerTests extends ApplicationTest {

    @Mock
    private IInterfaceOmTeDemostrerenDatDIWerkt _interfaceMock;

    AnchorPane childPane;

    @InjectMocks
    private SamenstellenController _controller;

    @Test
    public void test()  {
        when(_interfaceMock.getSampleText()).thenReturn("Henk");
        _controller.handleVraagToevoegenButtonAction(null);
    }
}
