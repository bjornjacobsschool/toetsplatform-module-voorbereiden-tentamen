package nl.han.toetsplatform.module.voorbereiden.data.sql;

import nl.han.toetsplatform.module.shared.storage.StorageDao;
import nl.han.toetsplatform.module.voorbereiden.data.SqlLoader;

import javax.inject.Inject;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
public class SqlDataBaseCreator {

    private final static Logger LOGGER = Logger.getLogger(SqlDataBaseCreator.class.getName());

    StorageDao storageDao;

    SqlLoader sqlLoader;

    @Inject
    public SqlDataBaseCreator(StorageDao storageDao, SqlLoader sqlLoader) {
        this.storageDao = storageDao;
        this.sqlLoader = sqlLoader;
    }

    public void create(){
        try {
            storageDao.executeUpdate(sqlLoader.load("DDL"));
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Could not create database: " + e.getMessage());
        }

    }
}
