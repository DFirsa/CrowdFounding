import Client.Client;
import Server.Server;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        Server server = new Server();
        Client client = new Client();
    }
}
