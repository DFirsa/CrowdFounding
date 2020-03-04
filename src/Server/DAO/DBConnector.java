package Server.DAO;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnector {

    public Connection connect() throws IOException, SQLException {

        File configFile = new File("config.properties");
        FileReader reader = new FileReader(configFile);
        Properties prop = new Properties();
        prop.load(reader);

         String url = "jdbc:mysql://" + prop.getProperty("host")
                + ":" + prop.getProperty("port") + "/"
                + prop.getProperty("database");

        String pword = prop.getProperty("password");
        String user = prop.getProperty("user");

        reader.close();

        return DriverManager.getConnection(url, user, pword);
    }
}
