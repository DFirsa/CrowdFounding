package Client;

import common.SocketPrintWriter;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {

    private static Socket clientSocket;
    private static BufferedReader reader;
    private static SocketPrintWriter printWriter;

    private static int port = 5000;

    public static void main(String[] args) throws IOException {

        try {

            enable();
            System.out.println("== Client ==");
            System.out.println("Enter commands. Enter help to get info about commands");

            String request = reader.readLine();

            printWriter.print(request);

            String serverAnswer = printWriter.read();
            System.out.println(">> " + serverAnswer);

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            System.out.println("Client has been closed");
            clientSocket.close();
            printWriter.close();
        }
    }

    private static void enable() throws IOException {
        clientSocket = new Socket("localhost", port);
        reader = new BufferedReader(new InputStreamReader(System.in));
        printWriter = new SocketPrintWriter(clientSocket.getInputStream(), clientSocket.getOutputStream());
    }

    private static void requestProcessing(String request) {
        System.out.println("sjdvbks");
    }

//    private static String[] parse(String input){
//
//        String[] result = input.split(" ");
//    }
}
