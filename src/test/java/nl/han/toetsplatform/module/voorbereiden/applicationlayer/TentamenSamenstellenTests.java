package nl.han.toetsplatform.module.voorbereiden.applicationlayer;

import nl.han.toetsplatform.module.shared.storage.StorageDao;
import nl.han.toetsplatform.module.voorbereiden.exceptions.GatewayCommunicationException;
import nl.han.toetsplatform.module.voorbereiden.models.Tentamen;
import nl.han.toetsplatform.module.voorbereiden.serviceagent.IGatewayServiceAgent;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.SQLException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class TentamenSamenstellenTests {
    private Tentamen _tentamen;

    @Mock
    private IGatewayServiceAgent _gatewayServiceAgentMock;

    @Mock
    private StorageDao _storageDAOMock;

    @InjectMocks
    private TentamenSamenstellen _sut;

    @Before
    public void initialize() throws SQLException {
        _tentamen = Mockito.spy(new Tentamen());
        _sut = Mockito.spy(_sut);
        doNothing().when(_sut).savetoDatabase(_tentamen);
    }

    @Test
    public void opslaanShouldCallGetOnGatewayServiceAgentWithCorrectParameter() throws GatewayCommunicationException, SQLException {
        _sut.opslaan(_tentamen);
        verify(_gatewayServiceAgentMock, times(1))
                .post("", _tentamen);
    }

    @Test
    public void opslaanShouldCallExecuteQueryOnStorageDao() throws GatewayCommunicationException, SQLException {
        _sut.opslaan(_tentamen);
        verify(_sut).savetoDatabase(_tentamen);
    }
}
