import java.sql.*;

public class Main {
    public static void main(String[] args) {
        try {
            Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/yourdatabase", 
                "yourusername", 
                "yourpassword"
            );

            EmployeeDatabaseImplementation dbImpl = new EmployeeDatabaseImplementation(connection);
            ReportGenerator reportGen = new ReportGenerator(dbImpl);

            UserInput ui = new UserInput(reportGen);

            ui.mainMenu();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
