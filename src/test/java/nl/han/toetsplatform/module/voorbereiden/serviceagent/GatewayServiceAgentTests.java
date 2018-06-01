package nl.han.toetsplatform.module.voorbereiden.serviceagent;

import org.junit.Assert;
import org.junit.Test;

public class GatewayServiceAgentTests {
    @Test
    public void testOfServiceAgentHalloWereldVanGatewayTerugKrijgt() {
        GatewayServiceAgent serviceAgent = new GatewayServiceAgent();

        try {
            String returnValue = serviceAgent.get("test");
            Assert.assertEquals("{\"message\":\"Hallo wereld!\"}",returnValue);
        }
        catch(Exception e)
        {
            Assert.fail();
        }
    }
}
