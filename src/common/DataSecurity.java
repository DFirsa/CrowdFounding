package common;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.UUID;

public class DataSecurity {

//    public static String givePrefix(){
//        return UUID.randomUUID().toString().replaceAll("-","");
//    }

    public static void SignIn(BufferedReader reader, SocketPrintWriter printWriter, String prefix) throws IOException {

        System.out.println("Login: ");
        String login = reader.readLine();
        System.out.println("Password: ");
        String pwd = reader.readLine();

        int pass = (pwd + prefix).hashCode();
        printWriter.send("signin " + login + " " + pass);
    }

    public static void SugnUp(String login, String password, SocketPrintWriter printWriter) throws IOException {
        int pass = password.hashCode();
        printWriter.send("signup " + login + " " + pass);
    }
}
