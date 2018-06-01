package nl.han.toetsplatform.module.voorbereiden.serviceagent;

import nl.han.toetsplatform.module.voorbereiden.exceptions.GatewayCommunicationException;
import nl.han.toetsplatform.module.voorbereiden.models.GatewayTestMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.*;

public class GatewayServiceAgentTests {
    private GatewayServiceAgent _sut;

    @BeforeEach
    public void initialize() {
        _sut = new GatewayServiceAgent();
    }

    @Test
    public void testOfServiceAgentHalloWereldVanGatewayTerugKrijgt() throws GatewayCommunicationException{// i
        GatewayTestMessage response = _sut.get("test", GatewayTestMessage.class);
        assertEquals("Hallo wereld!", response.message);
    }
}
