package nl.han.toetsplatform.module.voorbereiden.guice;

import com.google.gson.Gson;
import com.google.inject.AbstractModule;
import nl.han.toetsplatform.module.voorbereiden.applicationlayer.ITentamenKlaarzetten;
import nl.han.toetsplatform.module.voorbereiden.applicationlayer.ITentamenSamenstellen;
import nl.han.toetsplatform.module.voorbereiden.applicationlayer.TentamenKlaarzetten;
import nl.han.toetsplatform.module.voorbereiden.applicationlayer.TentamenSamenstellen;
import nl.han.toetsplatform.module.voorbereiden.data.SqlLoader;
import nl.han.toetsplatform.module.voorbereiden.data.TentamenDao;
import nl.han.toetsplatform.module.voorbereiden.data.VragenDao;
import nl.han.toetsplatform.module.voorbereiden.data.sql.SqlDataBaseCreator;
import nl.han.toetsplatform.module.voorbereiden.data.sql.SqlTentamenDao;
import nl.han.toetsplatform.module.voorbereiden.data.sql.SqlVragenDao;
import nl.han.toetsplatform.module.voorbereiden.serviceagent.GatewayServiceAgent;
import nl.han.toetsplatform.module.voorbereiden.serviceagent.IGatewayServiceAgent;
import nl.han.toetsplatform.module.voorbereiden.util.TentamenFile;

public class GuiceModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(ITentamenSamenstellen.class).to(TentamenSamenstellen.class);
        bind(IGatewayServiceAgent.class).to(GatewayServiceAgent.class);
        bind(ITentamenKlaarzetten.class).to(TentamenKlaarzetten.class);
        bind(TentamenFile.class);
        bind(TentamenDao.class).to(SqlTentamenDao.class);
        bind(VragenDao.class).to(SqlVragenDao.class);
        bind(SqlDataBaseCreator.class);
    }
}
