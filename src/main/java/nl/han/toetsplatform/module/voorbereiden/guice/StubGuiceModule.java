package nl.han.toetsplatform.module.voorbereiden.guice;

import com.google.inject.AbstractModule;
import nl.han.toetsplatform.module.shared.storage.StorageDao;
import nl.han.toetsplatform.module.voorbereiden.data.stub.StubStorageDao;

public class StubGuiceModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(StorageDao.class).to(StubStorageDao.class);
    }
}
