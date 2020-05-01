package Server;

import Server.DAO.DBConnector;
import common.SocketPrintWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Properties;

public class Server {

    private static final int port = 5000;

    private static SocketPrintWriter printWriter;
    public static LinkedList<ClientThread> serverList = new LinkedList<>();
    private static FileReader reader;
    private static Properties properties;

    public static void main(String[] args) throws IOException, SQLException {

        getBDProp();

        AdminConsole adminConsole = new AdminConsole();
        ServerSocket server = new ServerSocket(port);

        try {
            while (true) {

                Socket socket = server.accept();

                try {
                    serverList.add(new ClientThread(socket, properties));
                }
                catch (IOException e){
                    socket.close();
                }
            }
        }
        finally {
            server.close();
        }
    }

    public static void getBDProp() throws IOException {
        reader = new FileReader(new File("./src/configs/db.properties"));
        properties = new Properties();
        properties.load(reader);
    }
}
