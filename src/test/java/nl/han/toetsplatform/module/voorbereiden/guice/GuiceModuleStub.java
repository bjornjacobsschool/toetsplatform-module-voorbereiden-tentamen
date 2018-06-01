package nl.han.toetsplatform.module.voorbereiden.guice;

import com.google.inject.AbstractModule;
import nl.han.toetsplatform.module.voorbereiden.applicationlayer.IInterfaceOmTeDemostrerenDatDIWerkt;
import nl.han.toetsplatform.module.voorbereiden.applicationlayer.ImplementatieOmTeDemostrerenDatDIWerkt;

public class GuiceModuleStub extends AbstractModule {

    @Override
    protected void configure() {
        bind(IInterfaceOmTeDemostrerenDatDIWerkt.class).to(ImplementatieOmTeDemostrerenDatDIWerkt.class);
    }

}
