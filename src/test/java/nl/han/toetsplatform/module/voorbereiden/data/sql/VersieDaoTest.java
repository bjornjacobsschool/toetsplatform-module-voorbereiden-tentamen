package nl.han.toetsplatform.module.voorbereiden.data.sql;

import nl.han.toetsplatform.module.shared.storage.StorageDao;
import nl.han.toetsplatform.module.voorbereiden.data.SqlLoader;
import nl.han.toetsplatform.module.voorbereiden.data.stub.StubStorageDao;
import nl.han.toetsplatform.module.voorbereiden.models.Versie;
import nl.han.toetsplatform.module.voorbereiden.models.Vraag;
import org.junit.Before;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class VersieDaoTest {

    VersieDao versieDao;

    Versie versie = new Versie();

    @Before
    public void init(){
        SqlLoader sqlLoader = new SqlLoader();
        StorageDao storageDao = new StubStorageDao();
        SqlDataBaseCreator dataBaseCreator = new SqlDataBaseCreator(storageDao, sqlLoader);
        dataBaseCreator.create();
        versieDao = new VersieDao(storageDao, sqlLoader);

    }

    @Test
    public void testInsertVraag(){

    }

}