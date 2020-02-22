package Server;

import available.SocketPrintWriter;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private static final int port = 5000;

    private static ServerSocket server;
    private static SocketPrintWriter printWriter;

    public static void main(String[] args) throws IOException {
        try {
            server = new ServerSocket(port);
            System.out.println("Server has been ran");

            Socket clientSocket = server.accept();

            try {
                printWriter = new SocketPrintWriter(clientSocket.getInputStream(), clientSocket.getOutputStream());

                String fromClient = printWriter.read();
                System.out.println(fromClient);
                printWriter.print("Hi! I'm the server, and I know, that you have wrote: " + fromClient);

            }
            finally {
                System.out.println("Socket has closed");
                clientSocket.close();
                printWriter.close();
            }
        }
        finally {
            System.out.println("Server has closed");
            server.close();
        }
    }
}
