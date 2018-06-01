package nl.han.toetsplatform.module.voorbereiden.applicationlayer;

import nl.han.toetsplatform.module.voorbereiden.exceptions.GatewayCommunicationException;
import nl.han.toetsplatform.module.voorbereiden.models.Tentamen;
import nl.han.toetsplatform.module.voorbereiden.serviceagent.GatewayServiceAgent;

import javax.inject.Inject;

public class TentamenSamenstellen implements ITentamenSamenstellen {

    private GatewayServiceAgent _gatewayServiceAgent;

    @Inject
    public TentamenSamenstellen(GatewayServiceAgent gatewayServiceAgent)
    {
        this._gatewayServiceAgent = gatewayServiceAgent;
    }

    public void opslaan(Tentamen tentamen) throws GatewayCommunicationException {

        // tentamen versturen naar gateway
        // todo URL specificeren voor post request opslaan tentamen
        this._gatewayServiceAgent.post("", tentamen);

        // tentamen opslaan in lokale database
    }
}
