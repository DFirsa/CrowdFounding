package configs;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ConfigConnector {

    private FileReader reader;

    public Properties openConfig(String configName) throws IOException {

        File configFile = new File("./src/configs/" + configName);
        reader = new FileReader(configFile);
        Properties prop = new Properties();
        prop.load(reader);
        return prop;
    }

    public void close() throws IOException {
        reader.close();
    }
}
