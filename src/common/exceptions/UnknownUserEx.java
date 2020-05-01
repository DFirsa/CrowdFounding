package common.exceptions;

public class UnknownUserEx extends Exception {

    public UnknownUserEx(){
        super("Unauthorized user");
    }
}
