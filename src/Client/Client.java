package Client;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {

    private static Socket clientSocket;
    private static BufferedReader reader;
    private static BufferedReader in;
    private static BufferedWriter out;

    private static int port = 5000;

    public static void main(String[] args) throws IOException {

        try {
            clientSocket = new Socket("localhost", port);

            reader = new BufferedReader(new InputStreamReader(System.in));
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

            System.out.println("Write anything here:");
            String message = reader.readLine();
            out.write(message + "\n");
            out.flush();

            String serverAnswer = in.readLine();
            System.out.println(">> " + serverAnswer);

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            System.out.println("Client has been closed");
            clientSocket.close();
            in.close();
            out.close();
        }
    }
}
