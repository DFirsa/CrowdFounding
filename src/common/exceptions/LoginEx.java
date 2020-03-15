package common.exceptions;

public class LoginEx extends Exception {
    public LoginEx(){
        super("Incorrect login or password");
    }
}
