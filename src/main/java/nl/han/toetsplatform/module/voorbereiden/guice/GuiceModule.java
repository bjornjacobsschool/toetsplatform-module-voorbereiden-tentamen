package nl.han.toetsplatform.module.voorbereiden.guice;

import com.google.inject.AbstractModule;
import nl.han.toetsplatform.module.voorbereiden.classes.IInterfaceOmTeDemostrerenDatDIWerkt;
import nl.han.toetsplatform.module.voorbereiden.classes.ImplementatieOmTeDemostrerenDatDIWerkt;

public class GuiceModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(IInterfaceOmTeDemostrerenDatDIWerkt.class).to(ImplementatieOmTeDemostrerenDatDIWerkt.class);
    }

}
