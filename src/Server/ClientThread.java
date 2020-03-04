package Server;

import Server.Server;
import com.sun.jdi.connect.spi.Connection;
import common.SocketPrintWriter;

import java.io.IOException;
import java.net.Socket;
import java.util.Properties;

//TODO in every request connect to db and close it

public class ClientThread extends Thread {

    private Socket socket;
    private SocketPrintWriter printWriter;
    private java.sql.Connection connection;
    private Properties properties;

    public ClientThread(Socket socket, java.sql.Connection dbConnection, Properties properties) throws IOException{
        this.socket = socket;
        this.connection = dbConnection;
        this.properties = properties;
        printWriter = new SocketPrintWriter(socket.getInputStream(), socket.getOutputStream());
        start();
    }

    @Override
    public void run(){

        String input;

        try {

            while (true){

                input = printWriter.read();
                if(input.equals("stop")) break;

                String[] request = input.split(" ");

                switch (request[0]){

                    case ("add"):
                        //CodeForAddBalance
                        break;

                    case ("get"):
                        //CodeForGetBalance
                        break;

                    case ("give"):
                        //CodeForGiveMoneyToBalance
                        break;

                    case ("login"):
                        //CodeForLogin
                        break;
                }
            }
        } catch (IOException e) {
        }
    }

    private void send(String msg) {
        try {
            printWriter.send(msg);
        } catch (IOException ignored) {}
    }

}
