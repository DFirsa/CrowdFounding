package Server.DAO;

import Server.Server;

public interface IAdminOp {

    void createFund(String fundName);

    double getFundBalance(String fundName);
}