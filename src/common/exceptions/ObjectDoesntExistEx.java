package common.exceptions;

public class ObjectDoesntExistEx extends Exception {
    public ObjectDoesntExistEx(String name){
        super("Row with name " + name + " doesn't exist in the data base");
    }
}
