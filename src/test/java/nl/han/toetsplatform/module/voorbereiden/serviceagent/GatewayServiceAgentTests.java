package nl.han.toetsplatform.module.voorbereiden.serviceagent;

import nl.han.toetsplatform.module.voorbereiden.exceptions.GatewayCommunicationException;
import nl.han.toetsplatform.module.voorbereiden.models.GatewayTestMessage;
import org.junit.Test;

import static org.junit.Assert.*;

public class GatewayServiceAgentTests {
    @Test
    public void testOfServiceAgentHalloWereldVanGatewayTerugKrijgt() {
        GatewayServiceAgent serviceAgent = new GatewayServiceAgent();

        try {
            GatewayTestMessage response = serviceAgent.get("test", GatewayTestMessage.class);

            assertEquals("Hallo wereld!", response.message);
        }
        catch(GatewayCommunicationException e)
        {
            fail();
        }
    }
}
