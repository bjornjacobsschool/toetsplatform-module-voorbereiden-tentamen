package nl.han.toetsplatform.module.voorbereiden.guice;

import com.google.inject.AbstractModule;
import com.google.inject.Binder;
import nl.han.toetsplatform.module.voorbereiden.applicationlayer.ITentamenSamenstellen;
import nl.han.toetsplatform.module.voorbereiden.applicationlayer.TentamenSamenstellen;
import nl.han.toetsplatform.module.voorbereiden.data.SqlLoader;
import nl.han.toetsplatform.module.voorbereiden.data.TentamenDao;
import nl.han.toetsplatform.module.voorbereiden.data.sql.SqlTentamenDao;
import nl.han.toetsplatform.module.voorbereiden.data.sql.VersieDao;
import nl.han.toetsplatform.module.voorbereiden.serviceagent.GatewayServiceAgent;
import nl.han.toetsplatform.module.voorbereiden.serviceagent.IGatewayServiceAgent;

public class GuiceModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(ITentamenSamenstellen.class).to(TentamenSamenstellen.class);
        bind(IGatewayServiceAgent.class).to(GatewayServiceAgent.class);
        bind(SqlLoader.class);
        bind(TentamenDao.class).to(SqlTentamenDao.class);
        bind(VersieDao.class);
    }
}
