package Client;

import common.SocketPrintWriter;
import java.io.*;
import java.net.Socket;
import java.util.Scanner;


public class Client {

    private static Socket clientSocket;
    private static BufferedReader reader;
    private static SocketPrintWriter printWriter;
    private static String key;

    private static boolean loginL = false;

    private static final int port = 5000;

    public static void main(String[] args) throws IOException {

        try {

            enable();
            key = printWriter.read();

            System.out.println("== Client ==");
            System.out.println("Enter commands or enter help to get info about commands");

            while (true) {
                System.out.print("> ");
                String request = reader.readLine();
                commandExecutor(request);
            }

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

    private static void commandExecutor(String request) throws IOException {
        String[] parsed = request.split(" ");
        String answer;

        if (!isValid(parsed)) return;

        switch (parsed[0]){

            case ("help"):
                System.out.println("addBalance <amount> - operation to replanish you wallet.");
                System.out.println("getBalance - operation that return how much money you have at the wallet");
                System.out.println("giveTo <amount> <fund name> - operation for donating money to the fund");
                System.out.println("help - show operations info");
                break;

            case ("addMoney"):
                //add balance
                printWriter.send("add " + parsed[1]);
                answer = printWriter.read();
                if (answer.equals("success")) System.out.println("Your account successfully replenished");
                else System.out.println("An error occurred during the operation." +
                        "\nPlease try to repeat this operation later.");
                break;

            case("giveTo"):
                //give money to fund
                printWriter.send("give " + parsed[2] + " " + parsed[1]);
                answer = printWriter.read();
                if (answer.equals("success")) System.out.println("Money transferred to the '" +
                        parsed[1] + "' successfully");
                else if(answer.equals("money")) System.out.println("You haven't got enough money to do transfer");
                else if(answer.equals("fund")) System.out.println("The fund doesnt exist");
                else System.out.println("An error occurred during the operation." +
                            "\nPlease try to repeat this operation later.");
                break;

            case ("myBalance"):
                //show me balance
                printWriter.send("get");
                answer = printWriter.read();
                System.out.println(answer);
                break;

            case ("signIn"):
                //sign in
                if(loginL){
                    System.out.println("You are already login");
                    break;
                }

                System.out.print("Login: ");
                String login = reader.readLine();
                System.out.print("Password: ");

                int secureKey = (reader.readLine().hashCode() + key).hashCode();
                printWriter.send("signin " + login + " " + secureKey);

                answer = printWriter.read();
                if (answer.equals("success")){
                    System.out.println("Login was successful");
                    loginL = true;
                }
                else if(answer.equals("login")) System.out.println("Wrong login or password");
                else if(answer.equals("noUser")) System.out.println("This user doesn't exist");
                else System.out.println("An error occurred during the operation." +
                            "\nPlease try to repeat this operation later.");
                break;

            case ("signUp"):
                //signUp
                if(loginL){
                    System.out.println("You are already login");
                    break;
                }

                System.out.println("Fill in the fields to create account\n------------------------------------");
                System.out.print("Login: ");
                login = reader.readLine();

                if(!login.matches("^[a-zA-Z0-9]{1,32}$")){
                    System.out.println("Login contains unacceptable symbols or empty");
                    break;
                }

                System.out.print("Password: ");

                secureKey = reader.readLine().hashCode();
                printWriter.send("signup " + login + " " + secureKey);

                answer = printWriter.read();
                if(answer.equals("success")){
                    System.out.println("Account was created.\nLogin was successful.");
                    loginL = true;
                }
                else if(answer.equals("signup")) System.out.println("User with name " + login + " already exists");
                else System.out.println("An error occurred during the operation." +
                            "\nPlease try to repeat this operation later.");
                break;
        }
    }

    private static boolean isValid(String[] input){

        switch (input[0]){

            case("addMoney"):
                if (!loginL){
                    System.out.println("You must login to execute operations");
                    return false;
                }

                if(input.length == 2){
                    try{
                        double d = Double.parseDouble(input[1]);
                        if(d > 0) return true;
                        else System.out.println("All money amount must be greater then 0");
                    }
                    catch (NumberFormatException e){
                        return false;
                    }
                } else {
                    System.out.println("This operation must have an operand");
                    return false;
                }
                break;

            case ("giveTo"):

                if (!loginL){
                    System.out.println("You must login to execute operations");
                    return false;
                }

                if(input.length == 3){

                    try{
                        double d = Double.parseDouble(input[1]);
                        if (d > 0) return true;
                        else System.out.println("All money amount must be greater then 0");
                    }
                    catch (NumberFormatException e){
                        return false;
                    }
                } else {
                    System.out.println("This operation must have 2 operands");
                    return false;
                }

            case ("myBalance"):

                if (!loginL){
                    System.out.println("You must login to execute operations");
                    return false;
                }

                if (input.length != 1) System.out.println("This operation must has no operands");
                return input.length == 1;

            case("signIn"):
                if (input.length != 1) System.out.println("This operation must has no operands");
                return input.length == 1;

            case ("signUp"):
                if (input.length != 1) System.out.println("This operation must has no operands");
                return input.length == 1;

            case ("help"):
                if (input.length != 1) System.out.println("This operation must has no operands");
                return input.length == 1;

            default:
                System.out.println("UNKNOWN COMMAND\nTo get help enter help");
        }

        return false;
    }
}