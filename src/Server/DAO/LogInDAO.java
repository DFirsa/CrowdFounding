package Server.DAO;

import java.sql.Connection;

public class LogInDAO {

    private final Connection connection;

    public LogInDAO(Connection connection) {
        this.connection = connection;
    }
}
