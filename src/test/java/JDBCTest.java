import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.annotation.Testable;

import java.sql.*;

@Testable
public class JDBCTest {

    private static final String query = "select s.fio, g.name from test.student s " +
            "join test.grp g on g.id=s.group " +
            "WHERE fio = ? ";
    private static Connection connection;

    @BeforeAll
    static void init() throws SQLException {
        connection = getNewConnection();
    }

    @Test
    void shouldGetJDBCConnection() throws SQLException {
        try (Connection connection = getNewConnection()) {
            Assertions.assertTrue(connection.isValid(1));
            Assertions.assertFalse(connection.isClosed());
        }
    }

    @Test
    void shouldSelectData() throws SQLException {
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, "first");
        boolean hasResult = statement.execute();
        Assertions.assertTrue(hasResult);
        ResultSet resultSet = statement.getResultSet();
        resultSet.next();
        String fio = resultSet.getString("fio");
        Assertions.assertEquals("first", fio);
    }

    @AfterAll
    static void close() throws SQLException {
        connection.close();
    }

    private static Connection getNewConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/test";
        String username = "root";
        String password = "root";
        return DriverManager.getConnection(url, username, password);
    }
}
