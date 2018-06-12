package nl.han.toetsplatform.module.voorbereiden.data.sql;

import nl.han.toetsplatform.module.shared.storage.StorageDao;
import nl.han.toetsplatform.module.voorbereiden.data.SqlLoader;
import nl.han.toetsplatform.module.voorbereiden.data.stub.StubStorageDao;
import nl.han.toetsplatform.module.voorbereiden.models.Vraag;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
public class SqlVragenDaoTest {

    SqlVragenDao _vragenDao;

    Vraag vraag = new Vraag();

    @Before
    public void init(){
        SqlLoader sqlLoader = new SqlLoader();
        StorageDao storageDao = new StubStorageDao();
        SqlDataBaseCreator dataBaseCreator = new SqlDataBaseCreator(storageDao, sqlLoader);
        dataBaseCreator.create();
        _vragenDao = new SqlVragenDao(sqlLoader, storageDao, new VersieDao(storageDao, sqlLoader));
        vraag.setNaam("Test vraag");
    }

    @Test
    public void testInsertVraag(){
        List<Vraag> vragen = _vragenDao.getVragen();
        Assert.assertEquals(0, vragen.size());
        _vragenDao.insertVraag(vraag);
        vragen = _vragenDao.getVragen();
        Assert.assertEquals( 1, vragen.size());
    }

    @Test
    public void testInsertTentamenVraag(){

    }
}