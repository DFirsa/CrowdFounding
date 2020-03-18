package Client;

import common.SocketPrintWriter;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {

    private static Socket clientSocket;
    private static BufferedReader reader;
    private static SocketPrintWriter printWriter;
    private static String key;

    private static int port = 5000;

    public static void main(String[] args) throws IOException {

        try {

            enable();
            key = printWriter.read();

            System.out.println("== Client ==");
            System.out.println("Enter commands or enter help to get info about commands");

            while (true) {
                String request = reader.readLine();
                printWriter.send(request);

                String answer = printWriter.read();
                System.out.println(answer);
            }

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            clientSocket.close();
            printWriter.close();
        }
    }


    private static void enable () throws IOException {
        clientSocket = new Socket("localhost", port);
        reader = new BufferedReader(new InputStreamReader(System.in));
        printWriter = new SocketPrintWriter(clientSocket.getInputStream(), clientSocket.getOutputStream());
    }

    private void choosing(String request){
        String[] parsed = request.split(" ");

        switch (parsed[0]){

            case ("help"):
                //help message
                break;

            case ("addMoney"):
                //add balance
                break;

            case("giveTo"):
                //give money to fund
                break;

            case ("myBalance"):
                //show me balance
                break;

            case ("signIn"):
                //sign in
                break;

            case ("signUp"):
                //signUp
                break;
                
            default:
                System.out.println("UNKNOWN COMMAND\nTo get help enter help");
        }
    }
}