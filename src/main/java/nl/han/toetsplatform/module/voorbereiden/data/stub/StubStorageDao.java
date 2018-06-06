package nl.han.toetsplatform.module.voorbereiden.data.stub;

import nl.han.toetsplatform.module.shared.storage.StorageDao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StubStorageDao implements StorageDao {

    @Override
    public void setup(String s, String[] strings) throws SQLException {

    }

    @Override
    public ResultSet executeQuery(String s) throws SQLException {
        return null;
    }

    @Override
    public boolean executeUpdate(String s) throws SQLException {
        System.out.println(s);

        return true;
    }

    @Override
    public Connection getConnection() {
        return null;
    }

    @Override
    public void closeConnection() {

    }
}
