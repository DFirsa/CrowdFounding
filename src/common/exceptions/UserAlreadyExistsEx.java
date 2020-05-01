package common.exceptions;

public class UserAlreadyExistsEx extends Exception {
    public UserAlreadyExistsEx(){
        super("User already exists");
    }
}
