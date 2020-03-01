package Server;

import common.SocketPrintWriter;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;

//TODO there is admin cmd string

public class Server {

    private static final int port = 5000;

    private static SocketPrintWriter printWriter;
    public static LinkedList<ClientThread> serverList = new LinkedList<>();



    public static void main(String[] args) throws IOException {

        ServerSocket server = new ServerSocket(port);

        try {
            while (true) {

                Socket socket = server.accept();

                try {
                    serverList.add(new ClientThread(socket));
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
}
