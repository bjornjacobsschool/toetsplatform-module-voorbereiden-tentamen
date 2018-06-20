package nl.han.toetsplatform.module.voorbereiden.applicationlayer;

import nl.han.toetsapplicatie.apimodels.dto.KlaargezetTentamenDto;
import nl.han.toetsapplicatie.apimodels.dto.SamengesteldTentamenDto;
import nl.han.toetsapplicatie.apimodels.dto.VersieDto;
import nl.han.toetsplatform.module.voorbereiden.data.TentamenDao;
import nl.han.toetsplatform.module.voorbereiden.exceptions.GatewayCommunicationException;
import nl.han.toetsplatform.module.voorbereiden.serviceagent.IGatewayServiceAgent;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TentamenKlaarzettenTest {
    KlaargezetTentamenDto klaargezetTentamenDto;

    @Mock
    private IGatewayServiceAgent _gatewayServiceAgentMock;

    @Mock
    private TentamenDao _tentamenDao;

    @InjectMocks
    private TentamenKlaarzetten _tentamenKlaarzetten;

    @Before
    public void init(){
        klaargezetTentamenDto = new KlaargezetTentamenDto();
        klaargezetTentamenDto.setId(UUID.randomUUID());
    }

    @Test
    public void getSleutelTest() throws GatewayCommunicationException {
        String sleutel = "sleutel_test";
        doReturn(sleutel).when(_gatewayServiceAgentMock).get(Mockito.any(), Mockito.any());
        String realSleutel =  _tentamenKlaarzetten.getSleutel(klaargezetTentamenDto);
        Assert.assertEquals(sleutel, realSleutel);
        verify(_gatewayServiceAgentMock, times(1))
                .get("/tentamens/klaargezet/"+klaargezetTentamenDto.getId().toString() + "/sleutel", String.class);
    }

    @Test
    public void opslaanTest() throws GatewayCommunicationException, SQLException {
        _tentamenKlaarzetten.opslaan(klaargezetTentamenDto);

        verify(_tentamenDao).setTentamenKlaar(klaargezetTentamenDto);
        verify(_gatewayServiceAgentMock, times(1))
                .post("/tentamens/klaargezet", klaargezetTentamenDto);
    }

    @Test
    public void getKlaargezetteTentamensTest() throws GatewayCommunicationException {
        KlaargezetTentamenDto[] tentamens = new KlaargezetTentamenDto[1];
        KlaargezetTentamenDto klaargezetTentamenDto = new KlaargezetTentamenDto();
        tentamens[0] = klaargezetTentamenDto;
        doReturn(tentamens).when(_gatewayServiceAgentMock).get(Mockito.any(), Mockito.any());

        List<KlaargezetTentamenDto> realTentamens = _tentamenKlaarzetten.getKlaargezetteTentamens();
        Assert.assertEquals(tentamens.length, realTentamens.size());
        Assert.assertEquals(klaargezetTentamenDto, realTentamens.get(0));
    }

    @Test
    public void getTentamensTestOffline() throws GatewayCommunicationException {
        List<SamengesteldTentamenDto> localTentamens = new ArrayList<>();
        doThrow(new GatewayCommunicationException()).when(_gatewayServiceAgentMock).get(Mockito.any(), Mockito.any());
        doReturn(localTentamens).when(_tentamenDao).loadTentamens();
        List<SamengesteldTentamenDto> realTentamens = _tentamenKlaarzetten.getTentamens();
        Assert.assertEquals(localTentamens, realTentamens);
    }

    @Test
    public void getTentamensTestNewOnline() throws GatewayCommunicationException {
        List<SamengesteldTentamenDto> localTentamens = new ArrayList<>();
        SamengesteldTentamenDto[] serverTentamens = new SamengesteldTentamenDto[1];
        serverTentamens[0] = new SamengesteldTentamenDto();
        serverTentamens[0].setId(UUID.randomUUID());

        doReturn(localTentamens).when(_tentamenDao).loadTentamens();
        doReturn(serverTentamens).when(_gatewayServiceAgentMock).get(Mockito.any(), Mockito.any());


        List<SamengesteldTentamenDto> realTentamens = _tentamenKlaarzetten.getTentamens();

        verify(_tentamenDao, times(1)).saveTentamen(Mockito.any());

        Assert.assertEquals(localTentamens, realTentamens);
    }

    //Er worden 3 tentamens van de 'server' opgehaald waarvan er al 1 bestaat dus er moeten er 2 lokaal toegevoegd worden
    @Test
    public void getTentamensTestExistOnline() throws GatewayCommunicationException {
        List<SamengesteldTentamenDto> localTentamens = new ArrayList<>();
        SamengesteldTentamenDto[] serverTentamens = getOnlineVragen();
        localTentamens.add(serverTentamens[0]);
        doReturn(localTentamens).when(_tentamenDao).loadTentamens();
        doReturn(serverTentamens).when(_gatewayServiceAgentMock).get(Mockito.any(), Mockito.any());

        List<SamengesteldTentamenDto> realTentamens = _tentamenKlaarzetten.getTentamens();

        verify(_tentamenDao, times(2)).saveTentamen(Mockito.any());
    }

    private SamengesteldTentamenDto[] getOnlineVragen(){
        SamengesteldTentamenDto[] tentames = new SamengesteldTentamenDto[3];
        tentames[0] = new SamengesteldTentamenDto();
        tentames[1] = new SamengesteldTentamenDto();
        tentames[2] = new SamengesteldTentamenDto();
        tentames[0].setId(UUID.randomUUID());
        tentames[1].setId(UUID.randomUUID());
        tentames[2].setId(UUID.randomUUID());
        VersieDto versieDto = new VersieDto();
        versieDto.setNummer(1);
        tentames[0].setVersie(versieDto);
        tentames[1].setVersie(versieDto);
        tentames[2].setVersie(versieDto);
        return tentames;
    }
}