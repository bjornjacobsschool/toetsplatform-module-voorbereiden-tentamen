package nl.han.toetsplatform.module.voorbereiden.data.stub;

import nl.han.toetsplatform.module.shared.storage.StorageDao;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StubStorageDao implements StorageDao {


    private final static Logger LOGGER = Logger.getLogger(StubStorageDao.class.getName());

    private static Connection connection;

    public StubStorageDao() {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:file.db");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setup(String s, String[] strings) throws SQLException {
        Connection conn = getConnection();
        for(int i = 0; i < strings.length; i++){
            if(tableExists(conn, strings[i])){
                LOGGER.log(Level.INFO, "tables already exist not creating");
                return;
            }
        }
        Statement statement = conn.createStatement();
        statement.execute(s);
    }

    boolean tableExists(Connection conn, String table) throws SQLException {
        Statement statement = conn.createStatement();
        ResultSet rs = statement.executeQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='"+table+"';");
        boolean exists = false;
        while (rs.next()){
            exists = true;
        }
        return exists;
    }

    @Override
    public ResultSet executeQuery(String s) throws SQLException {
        Connection conn = getConnection();
        Statement statement = conn.createStatement();
        ResultSet resultSet = statement.executeQuery(s);
        return resultSet;
    }

    @Override
    public boolean executeUpdate(String s) throws SQLException {
        Connection conn = getConnection();
        Statement statement = conn.createStatement();
        int result = statement.executeUpdate(s);
        return result != -1;
    }

    @Override
    public Connection getConnection() {
        if(connection != null)
            return connection;

       /* try {
            //connection = DriverManager.getConnection("jdbc:sqlite::memory:");

            return connection;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Could not open connection");
        }*/
        return connection;
    }

    @Override
    public void closeConnection() {
     /*   try {
         //   connection.close();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Could not close connection");
        }
       // connection = null;*/
    }
}
