package common.exceptions;

public class ObjectAlreadyExistEx extends Exception {
    public ObjectAlreadyExistEx(String name){
        super("Object with name" + name + " already exist in the data base");
    }
}
