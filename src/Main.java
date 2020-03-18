import Client.Client;
import Server.Server;
import common.DataSecurity;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        DataSecurity ds = new DataSecurity();
        System.out.println(ds.givePrefix());
    }
}
