package nl.han.toetsplatform.module.voorbereiden.guice;

import com.google.inject.AbstractModule;
import nl.han.toetsplatform.module.voorbereiden.applicationlayer.ITentamenSamenstellen;
import nl.han.toetsplatform.module.voorbereiden.applicationlayer.TentamenSamenstellen;
import nl.han.toetsplatform.module.voorbereiden.serviceagent.GatewayServiceAgent;
import nl.han.toetsplatform.module.voorbereiden.serviceagent.IGatewayServiceAgent;

public class GuiceModuleStub extends AbstractModule {
    @Override
    protected void configure() {
        bind(ITentamenSamenstellen.class).to(TentamenSamenstellen.class);
        bind(IGatewayServiceAgent.class).to(GatewayServiceAgent.class);
    }
}
