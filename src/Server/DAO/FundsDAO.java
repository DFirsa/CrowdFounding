package Server.DAO;

import java.sql.Connection;

public class FundsDAO implements IAdminOp {

    private final Connection connection;

    public FundsDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void createFund(String fundName) {

    }

    @Override
    public double getFundBalance(String fundName) {
        return 0;
    }
}
