package nl.han.toetsplatform.module.voorbereiden.applicationlayer;

import nl.han.toetsplatform.module.voorbereiden.exceptions.GatewayCommunicationException;
import nl.han.toetsplatform.module.voorbereiden.models.Tentamen;
import nl.han.toetsplatform.module.voorbereiden.serviceagent.GatewayServiceAgent;

public class TentamenSamenstellen implements ITentamenSamenstellen {

    private GatewayServiceAgent _gatewayServiceAgent;

    public TentamenSamenstellen(GatewayServiceAgent gatewayServiceAgent)
    {
        this._gatewayServiceAgent = gatewayServiceAgent;
    }

    public void opslaan(Tentamen tentamen) throws GatewayCommunicationException {

        // tentamen versturen naar gateway
        this._gatewayServiceAgent.post("", tentamen);

        // tentamen opslaan in lokale database
    }
}
