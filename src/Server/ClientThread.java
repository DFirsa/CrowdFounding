package Server;

import common.SocketPrintWriter;

import java.io.IOException;
import java.net.Socket;
import java.util.Properties;
import java.util.UUID;

//TODO in every request connect to db and close it

public class ClientThread extends Thread {

    private Socket socket;
    private SocketPrintWriter printWriter;
    private Properties properties;
    private String key;

    public ClientThread(Socket socket, Properties properties) throws IOException{
        this.socket = socket;
        this.properties = properties;
        printWriter = new SocketPrintWriter(socket.getInputStream(), socket.getOutputStream());
        start();
    }

    @Override
    public void run(){

        String input;

        try {

            key = getSecureKey();
            send(key);

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

    private String getSecureKey(){
        return UUID.randomUUID().toString().replaceAll("-","");
    }
}
