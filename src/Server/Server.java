package Server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private static final int port = 5000;

    private static ServerSocket server;
    private static Socket clientSocket;
    private static BufferedReader in;
    private static BufferedWriter out;

    public static void main(String[] args) throws IOException {
        try {
            server = new ServerSocket(port);
            System.out.println("Server has been ran");

            clientSocket = server.accept();

            try {
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

                String fromClient = in.readLine();
                System.out.println(fromClient);
                out.write("Hi! I'm the server, and I know, that you have wrote: " + fromClient + "\n");
                out.flush();

            }
            finally {
                System.out.println("Socket has closed");
                clientSocket.close();

                in.close();
                out.close();
            }

        }
        finally {
            System.out.println("Server has closed");
            server.close();
        }
    }
}
