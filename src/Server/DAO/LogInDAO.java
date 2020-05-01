package Server.DAO;

import common.exceptions.LoginEx;
import common.exceptions.SignUpEx;
import common.exceptions.UserAlreadyExistsEx;
import configs.ConfigConnector;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class LogInDAO {

    private DBConnector connector;
    private ConfigConnector configConnector;

    public LogInDAO(){
        connector = new DBConnector();
        configConnector = new ConfigConnector();
    }

    //select @pwd, @userid  from @userInfo where @name = username
    public long signIn(String username, int key, String cookie) throws LoginEx, SQLException, IOException, UserAlreadyExistsEx {

        Connection connection = null;
        Properties properties;
        Statement statement = null;
        ResultSet resultSet = null;

        long result = -1;

        try{

            connection = connector.connect();
            properties = configConnector.openConfig("db.properties");

            String query = "SELECT " + properties.getProperty("pwd") + ", " + properties.getProperty("userid") +
                    " FROM " + properties.getProperty("userInfo") + " WHERE " + properties.getProperty("name") +
                    " = " + "'" + username + "'";

            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);

            int pwd = 0;
            boolean findSmth = false;
            while (resultSet.next()){
                findSmth = true;
                pwd = resultSet.getInt(1);
                result = resultSet.getLong(2);
            }

            if (!findSmth) throw new UserAlreadyExistsEx();
            if(key != (pwd + cookie).hashCode()) throw new LoginEx();

        } catch (SQLException e) {
            throw e;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LoginEx loginEx) {
            throw loginEx;
        } catch (UserAlreadyExistsEx e) {
            throw e;
        }
        finally {
            resultSet.close();
            statement.close();
            connection.close();
            configConnector.close();
        }

        return result;
    }

    public long signUp(String username, int key) throws SignUpEx, SQLException, IOException {

        Connection connection = null;
        Properties properties;
        Statement statement = null;
        ResultSet result = null;

        long id = -1;

        try{

            connection = connector.connect();
            properties = configConnector.openConfig("db.properties");

            String query = "SELECT COUNT(" + properties.getProperty("userid") + ") FROM "
                    + properties.getProperty("userInfo") + " WHERE " + properties.getProperty("name")
                    + " = " + "'" + username + "'";

            statement = connection.createStatement();
            result = statement.executeQuery(query);

            int count = 0;
            while (result.next()){ count = result.getInt(1); }
            if (count != 0) throw new SignUpEx(username);

            result.close();
            query = "INSERT INTO " + properties.getProperty("userInfo") + " ("
                    + properties.getProperty("name") + ", " + properties.getProperty("pwd")
                    + ") VALUES ('" + username + "', " + key + ")";

            statement.executeUpdate(query);

            query = "SELECT " + properties.getProperty("userid") + " FROM " + properties.getProperty("userInfo")
                    + " WHERE " + properties.getProperty("name") + " = " + "'" + username + "'";

            result = statement.executeQuery(query);
            while (result.next()){id = result.getLong(1);}

            query = "INSERT INTO " + properties.getProperty("money") + " (" + properties.getProperty("uid")
                    +", " + properties.getProperty("am") + ") VALUES (" + id + ", 0)";

            statement.executeUpdate(query);

        } catch (SQLException e) {
            throw e;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SignUpEx signUpEx) {
            throw signUpEx;
        }
        finally {
            if(result != null)result.close();
            if(statement != null)statement.close();
            if(connection != null)connection.close();
            if(configConnector != null)configConnector.close();
        }

        return id;
    }

}
