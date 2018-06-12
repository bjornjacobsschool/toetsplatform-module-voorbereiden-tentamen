package nl.han.toetsplatform.module.voorbereiden.data.sql;

import nl.han.toetsplatform.module.shared.storage.StorageDao;
import nl.han.toetsplatform.module.voorbereiden.data.SqlLoader;
import nl.han.toetsplatform.module.voorbereiden.data.stub.StubStorageDao;
import nl.han.toetsplatform.module.voorbereiden.models.Tentamen;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
public class SqlTentamenDaoTest {

    SqlTentamenDao sqlTentamenDao;

    Tentamen tentamen = new Tentamen();

    @Before
    public void init(){
        SqlLoader sqlLoader = new SqlLoader();
        StorageDao storageDao = new StubStorageDao();
        SqlDataBaseCreator dataBaseCreator = new SqlDataBaseCreator(storageDao, sqlLoader);
        VersieDao versieDao = new VersieDao(storageDao, sqlLoader);
        dataBaseCreator.create();

        sqlTentamenDao = new SqlTentamenDao(storageDao, sqlLoader, versieDao, new SqlVragenDao(sqlLoader, storageDao, versieDao));
        tentamen.setNaam("Test tenamen");
    }

    @Test
    public void testInsertAndSelect(){
        List<Tentamen> tentamen1 = sqlTentamenDao.loadTentamens();
        Assert.assertEquals(0 , tentamen1.size());
        sqlTentamenDao.saveTentamen(tentamen);
        List<Tentamen> tentamen2 = sqlTentamenDao.loadTentamens();
        Assert.assertEquals(1, tentamen2.size());
    }

    @Test
    public void testSelectWrong(){
        List<Tentamen> tentamen1 = sqlTentamenDao.loadTentamens();
        Assert.assertNotEquals(99 , tentamen1.size());
    }
}