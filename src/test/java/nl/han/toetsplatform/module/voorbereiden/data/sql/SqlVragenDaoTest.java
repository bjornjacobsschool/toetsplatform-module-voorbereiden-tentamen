package nl.han.toetsplatform.module.voorbereiden.data.sql;

import nl.han.toetsapplicatie.apimodels.dto.VersieDto;
import nl.han.toetsapplicatie.apimodels.dto.VragenbankVraagDto;
import nl.han.toetsplatform.module.shared.storage.StorageDao;
import nl.han.toetsplatform.module.voorbereiden.data.SqlLoader;
import nl.han.toetsplatform.module.voorbereiden.data.stub.StubStorageDao;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;
import java.util.UUID;

@RunWith(MockitoJUnitRunner.class)
public class SqlVragenDaoTest {

    SqlVragenDao _vragenDao;

    VragenbankVraagDto vraag = new VragenbankVraagDto();

    @Before
    public void init(){
        SqlLoader sqlLoader = new SqlLoader();
        StorageDao storageDao = new TestStorageDao();
        SqlDataBaseCreator dataBaseCreator = new SqlDataBaseCreator(storageDao, sqlLoader);
        dataBaseCreator.create();
        _vragenDao = new SqlVragenDao(sqlLoader, storageDao);
        vraag.setNaam("Test vraag");
        vraag.setId(UUID.randomUUID());
        vraag.setVersie(new VersieDto());
    }

    @Test
    public void testInsertVraag(){
        List<VragenbankVraagDto> vragen = _vragenDao.getVragen();
        int before= vragen.size();
        _vragenDao.insertVraag(vraag);
        vragen = _vragenDao.getVragen();
        Assert.assertEquals( before + 1, vragen.size());
    }
}