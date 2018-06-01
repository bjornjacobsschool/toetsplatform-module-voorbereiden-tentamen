package nl.han.toetsplatform.module.voorbereiden.applicationlayer;

import nl.han.toetsplatform.module.shared.storage.StorageDao;
import nl.han.toetsplatform.module.voorbereiden.exceptions.GatewayCommunicationException;
import nl.han.toetsplatform.module.voorbereiden.models.Tentamen;
import nl.han.toetsplatform.module.voorbereiden.serviceagent.IGatewayServiceAgent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.SQLException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class TentamenSamenstellenTests {
    private Tentamen _tentamen;

    @Mock
    private IGatewayServiceAgent _gatewayServiceAgentMock;

    @Mock
    private StorageDao _storageDAOMock;

    @InjectMocks
    private TentamenSamenstellen _sut;

    @BeforeEach
    private void initialize() {
        _tentamen = new Tentamen();
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
        verify(_storageDAOMock, times(1))
                .executeQuery(any(String.class));
    }
}
