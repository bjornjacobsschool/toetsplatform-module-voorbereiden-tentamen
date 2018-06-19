package nl.han.toetsplatform.module.voorbereiden.data.sql;

import nl.han.toetsplatform.module.voorbereiden.data.stub.StubStorageDao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TestStorageDao extends StubStorageDao {

    private Connection connection;

    @Override
    public Connection getConnection() {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:mydb.db");
            return connection;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
