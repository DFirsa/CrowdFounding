package Client;

import available.SocketPrintWriter;

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
            System.out.println("------------");

            while (true){

                String request = reader.readLine();
                String[] parsedRequest = request.split(" ");

                if(parsedRequest[0].equals("quit")) break;
                if(continueCondition(parsedRequest)) {
                    System.out.println(">> Illegal syntax!");
                    continue;
                }

                printWriter.print(request);
                String answer = printWriter.read();
                System.out.println(">> " + answer);
            }

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

    private static boolean continueCondition(String[] requestParts){
        return (requestParts.length > 3)
                || (requestParts.length == 3 && !requestParts[0].equals("giveToFund"))
                || (requestParts.length == 2 && !requestParts[0].equals("addBalance"))
                || (requestParts.length == 1 && !requestParts[0].equals("getBalance"));
    }
}
