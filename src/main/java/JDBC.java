import java.sql.*;

import static java.lang.System.out;

public class JDBC {
    private static final String url = "jdbc:mysql://localhost:3306/test";
    private static final String user = "root";
    private static final String password = "root";

    private static Connection connection;
    private static PreparedStatement statement;
    private static ResultSet rs;

    private static final String query = "select s.fio, g.name from test.student s " +
            "join test.grp g on g.id=s.group " +
            "WHERE fio = ? ";

    public static void main(String[] args) {
        try {
            connection = DriverManager.getConnection(url, user, password);
            out.println("Соединение с СУБД выполнено.");
            statement = connection.prepareStatement(query);
            rs = statement.executeQuery();
            while (rs.next()) {
                String fio = rs.getString("fio");
                String group = rs.getString("group");
                out.printf("fio: %s gr: %s\n", fio, group);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            out.println("Ошибка SQL!");
        } finally {
            try {
                if (connection != null) connection.close();
                out.println("Отключение от СУБД выполнено.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (statement != null) statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (rs != null) rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }
}
