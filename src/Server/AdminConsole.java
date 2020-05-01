package Server;

import Server.DAO.FundsDAO;
import common.exceptions.ObjectAlreadyExistEx;
import common.exceptions.ObjectDoesntExistEx;

import java.io.*;
import java.sql.SQLException;
import java.util.Scanner;

public class AdminConsole extends Thread {

    private FundsDAO fundsDAO;

    public AdminConsole(){
        fundsDAO = new FundsDAO();
        start();
    }

    @Override
    public void run() {

        Scanner sc = new Scanner(System.in);

        while (true){

            String cmd = sc.nextLine();
            String[] parsed = cmd.split(" ");

            if(!isValid(parsed))continue;

            switch (parsed[0]){

                case ("create"):
                    try{
                        fundsDAO.createFund(parsed[1]);
                        System.out.println("fund " + parsed[1] + " created");
                    } catch (ObjectAlreadyExistEx objectAlreadyExistEx) {
                        System.out.println("fund " + parsed[1] + " already exists");
                    } catch (IOException e) {
                        System.out.println("Something is going wrong");
                    } catch (SQLException e) {
                        System.out.println("Data base exception");
                    }
                    break;

                case ("balance"):
                    try {
                        double b = fundsDAO.getFundBalance(parsed[1]);
                        System.out.println("Balance of " + parsed[1] + ": " + b + "$");
                    } catch (ObjectDoesntExistEx objectDoesntExistEx) {
                        System.out.println("fund " + parsed[1] + "doesnt exist");
                    } catch (IOException e) {
                        System.out.println("Something is going wrong");
                    } catch (SQLException e) {
                        System.out.println("Data base exception");
                    }
                    break;

                case ("help"):
                    System.out.println("create <fund name> - operation for creating the fund");
                    System.out.println("balance <fund name> - operation for get balance of the fund");
                    System.out.println("help - operation for get information about commands");
                    break;
            }
        }
    }

    private boolean isValid(String[] cmd){
        if(cmd[0].equals("create") || cmd[0].equals("balance")) return cmd.length == 2;
        if(cmd[0].equals("help")) return cmd.length == 1;
        System.out.println("UNKNOWN COMMAND\nTo get help enter help");
        return false;
    }
}
