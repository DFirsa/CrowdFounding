package Server.DAO;

import Client.IUserOp;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class WalletsDAO implements IUserOp{

    private final Connection connection;
    private Properties properties;
    private FileReader fileReader;

    public WalletsDAO(Connection connection, Properties properties) throws FileNotFoundException {
        this.connection = connection;
        this.properties = properties;

        fileReader = new FileReader(new File("db.properties"));
    }

    @Override
    public void addBalance(long userId) {
//        String query = "Select "
    }

    @Override
    public void giveToFund(long userId, String fundName, double amount) {





    }

    @Override
    public double getBalance(long userId) throws SQLException {

        String query = getQueryGenerator(properties.getProperty("am"),
                properties.getProperty("money"), properties.getProperty("uid")
                        + " is " + userId);

        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(query);

        double result = 0;
        while (rs.next()){
            result = rs.getDouble(1);
        }
        return result;
    }

    private String getQueryGenerator(String what, String from, String conditions){
        return "Select " + what + " from " + from + " where " + conditions;
    }
}
