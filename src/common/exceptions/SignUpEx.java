package common.exceptions;

public class SignUpEx extends Exception {
    public SignUpEx(String login){
        super("User with name " + login + " already exist");
    }
}
