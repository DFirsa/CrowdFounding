package Server;

import Server.DAO.LogInDAO;
import Server.DAO.WalletsDAO;
import common.SocketPrintWriter;
import common.exceptions.*;

import java.io.IOException;
import java.net.Socket;
import java.sql.SQLException;
import java.util.Properties;
import java.util.UUID;

public class ClientThread extends Thread {

    private Socket socket;
    private SocketPrintWriter printWriter;
    private Properties properties;
    private String key;

    private WalletsDAO walletsDAO;
    private LogInDAO logInDAO;

    private long id = -1;

    public ClientThread(Socket socket, Properties properties) throws IOException{

        walletsDAO = new WalletsDAO();
        logInDAO = new LogInDAO();

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
                        try{
                            walletsDAO.addBalance(id, Double.parseDouble(request[1]));
                            printWriter.send("success");
                        }
                        catch (SQLException e){printWriter.send("crush");}
                        break;

                    case ("get"):
                        //CodeForGetBalance
                        try{
                            printWriter.send("Your balance: " + walletsDAO.getBalance(id) + " $");
                        }
                        catch (Exception e){printWriter.send("Something went wrong");}
                        break;

                    case ("give"):
                        //CodeForGiveMoneyToBalance
                        try{
                            walletsDAO.giveToFund(id, request[1], Double.parseDouble(request[2]));
                            printWriter.send("success");
                        } catch (NotEnoughMoneyEx notEnoughMoneyEx) {
                            printWriter.send("money");
                        } catch (SQLException e) {
                            printWriter.send("crush");
                        } catch (ObjectDoesntExistEx objectDoesntExistEx) {
                            printWriter.send("fund");
                        }
                        break;

                    case ("signin"):
                        //CodeForLogin
                        try{
                            id = logInDAO.signIn(request[1], Integer.parseInt(request[2]), key);
                            printWriter.send("success");
                        } catch (LoginEx loginEx) {
                            printWriter.send("login");
                        } catch (SQLException e) {
                            printWriter.send("crush");
                        } catch (UserAlreadyExistsEx userAlreadyExistsEx) {
                            printWriter.send("noUser");
                        }
                        break;

                    case ("signup"):
                        //code for sign up
                        try {
                            id = logInDAO.signUp(request[1], Integer.parseInt(request[2]));
                            printWriter.send("success");
                        } catch (SignUpEx signUpEx) {
                            printWriter.send("signup");
                        } catch (SQLException e) {
                            printWriter.send("crush");
                        }
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
