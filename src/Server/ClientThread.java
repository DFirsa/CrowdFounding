package Server;

import Server.Server;
import common.SocketPrintWriter;

import java.io.IOException;
import java.net.Socket;

public class ClientThread extends Thread {

    private Socket socket;
    private SocketPrintWriter printWriter;

    public ClientThread(Socket socket) throws IOException{
        this.socket = socket;
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
