package nl.han.toetsplatform.module.voorbereiden.guice;

import com.google.gson.Gson;
import com.google.inject.AbstractModule;
import nl.han.toetsplatform.module.voorbereiden.applicationlayer.ITentamenKlaarzetten;
import nl.han.toetsplatform.module.voorbereiden.applicationlayer.ITentamenSamenstellen;
import nl.han.toetsplatform.module.voorbereiden.applicationlayer.TentamenKlaarzetten;
import nl.han.toetsplatform.module.voorbereiden.applicationlayer.TentamenSamenstellen;
import nl.han.toetsplatform.module.voorbereiden.serviceagent.GatewayServiceAgent;
import nl.han.toetsplatform.module.voorbereiden.serviceagent.IGatewayServiceAgent;
import nl.han.toetsplatform.module.voorbereiden.util.TentamenFile;

public class GuiceModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(ITentamenSamenstellen.class).to(TentamenSamenstellen.class);
        bind(IGatewayServiceAgent.class).to(GatewayServiceAgent.class);
        bind(ITentamenKlaarzetten.class).to(TentamenKlaarzetten.class);
        bind(Gson.class);
        bind(TentamenFile.class);
        bind(SqlLoader.class);
        bind(TentamenDao.class).to(SqlTentamenDao.class);
        bind(VersieDao.class);
    }
}
