package common.exceptions;

public class NotEnoughMoneyEx extends Exception {
    public NotEnoughMoneyEx(){
        super("Not enough funds to complete the operations");
    }
}
