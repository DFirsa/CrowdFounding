package Server;

import common.SocketPrintWriter;

import java.io.IOException;
import java.net.Socket;

public class ServerThread extends Thread{

    private Socket socket;
    private SocketPrintWriter printWriter;

    public ServerThread(Socket socket) throws IOException {
        this.socket = socket;
        printWriter = new SocketPrintWriter(socket.getInputStream(), socket.getOutputStream());
        start();
    }

//    @Override
//    public void run(){
//        while(true){
//
//        }
//    }
}
