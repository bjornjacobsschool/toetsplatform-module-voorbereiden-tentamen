package nl.han.toetsplatform.module.voorbereiden.serviceagent;

import nl.han.toetsplatform.module.voorbereiden.exceptions.GatewayCommunicationException;
import nl.han.toetsplatform.module.voorbereiden.models.GatewayTestMessage;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class GatewayServiceAgentTests {
    private GatewayServiceAgent _sut;

    @Before
    public void initialize() {
        _sut = new GatewayServiceAgent();
    }

    @Test
    public void testOfServiceAgentHalloWereldVanGatewayTerugKrijgt() throws GatewayCommunicationException{// i
        GatewayTestMessage response = _sut.get("test/Henk", GatewayTestMessage.class);
        assertEquals("Welkom Henk", response.message);
    }
}
