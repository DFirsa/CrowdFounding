package Client;



import common.exceptions.NotEnoughMoneyEx;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;

public interface IUserOp {

    void addBalance(long userId, double amount) throws IOException, SQLException;

    void giveToFund(long userId, String fundName, double amount) throws IOException, SQLException, NotEnoughMoneyEx;

    double getBalance(long userId) throws SQLException, IOException;
}
