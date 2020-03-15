package common.exceptions;

public class UnknownOperationEx extends Exception{
    public UnknownOperationEx(String operationName){
        super("Unknown operation " + operationName);
    }
}
