package Server.DAO;

import Server.Server;
import common.exceptions.ObjectAlreadyExistEx;
import common.exceptions.ObjectDoesntExistEx;

import java.io.IOException;
import java.sql.SQLException;

public interface IAdminOp {

    void createFund(String fundName) throws IOException, SQLException, ObjectAlreadyExistEx;

    double getFundBalance(String fundName) throws IOException, SQLException, ObjectDoesntExistEx;
}
