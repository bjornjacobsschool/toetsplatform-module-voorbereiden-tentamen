package nl.han.toetsplatform.module.voorbereiden.controllers;


import nl.han.toetsplatform.module.voorbereiden.temp.IInterfaceOmTeDemostrerenDatDIWerkt;;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SamenstellenControllerTests {

    @Mock
    private IInterfaceOmTeDemostrerenDatDIWerkt _interfaceMock;

    @InjectMocks
    private SamenstellenController _controller;

    @Test
    public void test()  {
        when(_interfaceMock.getSampleText()).thenReturn("Henk");
        _controller.handleTentamenOpslaanButtonAction(null);
    }
}
