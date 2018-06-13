package nl.han.toetsplatform.module.voorbereiden.data.sql;

import nl.han.toetsapplicatie.apimodels.dto.SamengesteldTentamenDto;
import nl.han.toetsplatform.module.shared.storage.StorageDao;
import nl.han.toetsplatform.module.voorbereiden.data.SqlLoader;
import nl.han.toetsplatform.module.voorbereiden.data.stub.StubStorageDao;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class SqlTentamenDaoTest {

    SqlTentamenDao sqlTentamenDao;

    SamengesteldTentamenDto tentamen = new SamengesteldTentamenDto();

    @Before
    public void init(){
        SqlLoader sqlLoader = new SqlLoader();
        StorageDao storageDao = new StubStorageDao();
        SqlDataBaseCreator dataBaseCreator = new SqlDataBaseCreator(storageDao, sqlLoader);
        dataBaseCreator.create();

        sqlTentamenDao = new SqlTentamenDao(storageDao, sqlLoader, new SqlVragenDao(sqlLoader, storageDao));
        tentamen.setNaam("Test tenamen");
    }

    @Test
    public void testInsertAndSelect(){
        List<SamengesteldTentamenDto> tentamen1 = sqlTentamenDao.loadTentamens();
        Assert.assertEquals(0 , tentamen1.size());
        sqlTentamenDao.saveTentamen(tentamen);
        List<SamengesteldTentamenDto> tentamen2 = sqlTentamenDao.loadTentamens();
        Assert.assertEquals(1, tentamen2.size());
    }

    @Test
    public void testSelectWrong(){
        List<SamengesteldTentamenDto> tentamen1 = sqlTentamenDao.loadTentamens();
        Assert.assertNotEquals(99 , tentamen1.size());
    }
}