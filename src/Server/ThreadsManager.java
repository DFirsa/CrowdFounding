package Server;

import common.SocketPrintWriter;

import java.util.LinkedList;

public class ThreadsManager extends Thread{

    private static final int port = 5000;

    private static SocketPrintWriter printWriter;
    public static LinkedList<ClientThread> serverList = new LinkedList<>();

    //TODO there is threads manager (Code from Server)
    @Override
    public void run() {

//        ServerSocket server = new ServerSocket(port);
//
//        while (true){
//
//            Socket socket = se
//        }


    }
}
