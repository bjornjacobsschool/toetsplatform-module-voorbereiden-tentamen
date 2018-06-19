package nl.han.toetsplatform.module.voorbereiden.applicationlayer;

import nl.han.toetsapplicatie.apimodels.dto.SamengesteldTentamenDto;
import nl.han.toetsapplicatie.apimodels.dto.VragenbankVraagDto;
import nl.han.toetsplatform.module.voorbereiden.data.TentamenDao;
import nl.han.toetsplatform.module.voorbereiden.data.VragenDao;
import nl.han.toetsplatform.module.voorbereiden.exceptions.GatewayCommunicationException;
import nl.han.toetsplatform.module.voorbereiden.serviceagent.IGatewayServiceAgent;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.SQLException;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class TentamenSamenstellenTests {
    private SamengesteldTentamenDto _tentamen;

    private VragenbankVraagDto _vraag;

    @Mock
    private IGatewayServiceAgent _gatewayServiceAgentMock;

    @Mock
    private TentamenDao _tentamenDao;

    @Mock
    private VragenDao _vragenDao;

    @InjectMocks
    private TentamenSamenstellen _sut;

    @Before
    public void initialize() throws SQLException {
        _tentamen = Mockito.spy(new SamengesteldTentamenDto());
        _vraag = new VragenbankVraagDto();
    }

    @Test
    public void slaTentamenTest() throws GatewayCommunicationException, SQLException {
        _sut.slaTentamenOp(_tentamen);
        verify(_gatewayServiceAgentMock, times(1))
                .post("tentamens/samengesteld", _tentamen);
        verify(_tentamenDao).saveTentamen(_tentamen);
    }


    @Test
    public void slaVraagOpTest() throws GatewayCommunicationException {
        _sut.slaVraagOp(_vraag);
        verify(_vragenDao).insertVraag(_vraag);
        verify(_gatewayServiceAgentMock).post("vragenbank", _vraag);
    }

    @Test
    public void getVragenTest(){

    }

    private VragenbankVraagDto[] getServerVragen(){
        VragenbankVraagDto[] vragen = new VragenbankVraagDto[3];
        vragen[0] = new VragenbankVraagDto();
        vragen[1] = new VragenbankVraagDto();
        vragen[2] = new VragenbankVraagDto();


    }
}
