package Server.DAO;

import Client.IUserOp;

import common.exceptions.NotEnoughMoneyEx;
import configs.ConfigConnector;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class WalletsDAO implements IUserOp{

    private DBConnector connector;
    private ConfigConnector configConnector;

    public WalletsDAO() throws FileNotFoundException {
        connector = new DBConnector();
        configConnector = new ConfigConnector();
    }

    //UPDATE @money SET @am = amount + @am WHERE @uid = userId;
    @Override
    public void addBalance(long userId, double amount) throws IOException, SQLException {

        Connection connection = null;
        Properties properties;
        Statement statement = null;

        try{
            connection = connector.connect();
            properties = configConnector.openConfig("db.properties");

            String query = "UPDATE " + properties.getProperty("money") + " SET " + properties.getProperty("am") +
                    " = " + amount + " + " + properties.getProperty("am") + " WHERE " + properties.getProperty("uid") +
                    " = " + userId;

            statement = connection.createStatement();
            statement.executeQuery(query);
        }
        finally {
            statement.close();
            connection.close();
            configConnector.close();
        }
    }

    //SELECT @am from @money WHERE @uid = userId
    //UPDATE @money SET @am = amount - @am WHERE @uid = userId;
    //UPDATE @funds SET @am = amount + @am WHERE @fund = fundName
    @Override
    public void giveToFund(long userId, String fundName, double amount) throws IOException, SQLException, NotEnoughMoneyEx {

        Connection connection = null;
        Properties properties;
        Statement statement = null;
        ResultSet rs = null;

        try{

            connection = connector.connect();
            properties = configConnector.openConfig("db.properties");

            String query = getQueryGenerator(properties.getProperty("am"), properties.getProperty("money"),
                    properties.getProperty("uid") + " = " + userId);

            statement =  connection.createStatement();
            rs = statement.executeQuery(query);

            double money = 0;
            while (rs.next()){
                money = rs.getDouble(1);
            }
            if (money < amount) throw new NotEnoughMoneyEx();


            query = "UPDATE " + properties.getProperty("money") + " SET " + properties.getProperty("am") +
                    " = " + amount + " - " + properties.getProperty("am") + " WHERE " + properties.getProperty("uid") +
                    " = " + userId;

            statement.executeQuery(query);

            query = "UPDATE " + properties.getProperty("funds") + " SET " + properties.getProperty("am") +
                    " = " + amount + " + " + properties.getProperty("am") + " WHERE " + properties.getProperty("fund") +
                    " = " + fundName;

            statement.execute(query);

        }
        catch (NotEnoughMoneyEx e){
            throw e;
        }
        finally {
            rs.close();
            statement.close();
            connection.close();
            configConnector.close();
        }
    }

    @Override
    public double getBalance(long userId) throws SQLException, IOException {

        Connection connection = null;
        Properties properties;
        Statement statement = null;
        ResultSet rs = null;

        try{
            connection = connector.connect();
            properties = configConnector.openConfig("db.properties");

            String query = getQueryGenerator(properties.getProperty("am"),
                    properties.getProperty("money"), properties.getProperty("uid")
                            + " = " + userId);

            statement = connection.createStatement();
            rs = statement.executeQuery(query);

            double result = 0;
            while (rs.next()){
                result = rs.getDouble(1);
            }

            return result;
        }
        finally {
            rs.close();
            statement.close();
            configConnector.close();
            connection.close();
        }
    }

    private String getQueryGenerator(String what, String from, String conditions){
        return "Select " + what + " from " + from + " where " + conditions;
    }
}
