package Server.DAO;

import common.exceptions.ObjectAlreadyExistEx;
import common.exceptions.ObjectDoesntExistEx;
import configs.ConfigConnector;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class FundsDAO implements IAdminOp {

    private DBConnector connector;
    private ConfigConnector configConnector;

    public FundsDAO() {
        connector = new DBConnector();
        configConnector = new ConfigConnector();
    }

    //SELECT COUNT(@fund) FROM @funds WHERE @fund = fundName
    //INSERT INTO @funds VALUES ('@fund', 0)
    @Override
    public void createFund(String fundName) throws IOException, SQLException, ObjectAlreadyExistEx {

        Connection connection = null;
        Properties properties;
        Statement statement = null;
        ResultSet rs = null;

        try{
            connection = connector.connect();
            properties = configConnector.openConfig("db.properties");

            String query = "SELECT COUNT(" + properties.getProperty("fund") + ") FROM " +
                    properties.getProperty("funds") + " WHERE " + properties.getProperty("fund") +
                    " = " + "'" + fundName + "'";

            statement = connection.createStatement();
            rs = statement.executeQuery(query);

            int count = 0;
            while (rs.next()){
                count = rs.getInt(1);
            }

            if (count != 0) throw new ObjectAlreadyExistEx(fundName);

            query = "INSERT INTO " + properties.getProperty("funds") + " (" + properties.getProperty("fund") +
                    ", " + properties.getProperty("am") + ") " + "VALUES ( '" + fundName + "', 0)";

            statement.executeUpdate(query);
        }
        catch (ObjectAlreadyExistEx e){
            throw e;
        }
        finally {
            if(rs != null)rs.close();
            if(statement != null)statement.close();
            if(connection != null)connection.close();
            if(configConnector != null)configConnector.close();
        }
    }

    //SELECT @am FROM @funds WHERE @fund = fundName
    @Override
    public double getFundBalance(String fundName) throws IOException, SQLException, ObjectDoesntExistEx {

        Connection connection = null;
        Properties properties;
        Statement statement = null;
        ResultSet rs = null;

        try{

            connection = connector.connect();
            properties = configConnector.openConfig("db.properties");

            String query = "SELECT " + properties.getProperty("am") + " FROM " +
                    properties.getProperty("funds") + " WHERE " +
                    properties.getProperty("fund") + " = " + "'" + fundName + "'";

            statement = connection.createStatement();
            rs = statement.executeQuery(query);

            double amount = -1;
            while (rs.next()){
                amount = rs.getDouble(1);
            }

            if (amount == -1) throw new ObjectDoesntExistEx(fundName);
            return amount;

        }
        catch (ObjectDoesntExistEx e){
            throw e;
        }
        finally {
            if(rs != null)rs.close();
            if(statement != null)statement.close();
            if(connection != null)connection.close();
            if(configConnector != null)configConnector.close();
        }
    }
}
