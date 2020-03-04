package Client;

import java.math.BigDecimal;
import java.sql.SQLException;

public interface IUserOp {

    void addBalance(long userId);

    void giveToFund(long userId, String fundName, double amount);

    double getBalance(long userId) throws SQLException;
}
