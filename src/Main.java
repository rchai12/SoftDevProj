import java.sql.*;

public class Main {
    public static void main(String[] args) {
        try {
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/your_database_name",
                    "your_username",
                    "your_password"
            );

            EmployeeDatabaseImplementation dbImpl = new EmployeeDatabaseImplementation(connection);
            ReportGenerator reportGen = new ReportGenerator(dbImpl);

            // You can now call methods on reportGen, e.g.:
            // reportGen.generateEmployeeInfo(...);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
