import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import Employee from employee;

public class EmployeeDatabaseImplementation implements EmployeeDatabaseInterface {

    private Connection connection;

    public EmployeeDatabaseImplementation(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void addEmployee(Employee newEmployee) {
        Statement statement = null;
        try {
            statement = connection.createStatement();
            String sql = "INSERT INTO employees (employee_id, first_name, last_name, ssn, job_title, division, salary) VALUES (" +
                        newEmployee.getEmployeeId() + ", '" +
                        newEmployee.getFirstName() + "', '" +
                        newEmployee.getLastName() + "', '" +
                        newEmployee.getSsn() + "', '" +
                        newEmployee.getJobTitle() + "', '" +
                        newEmployee.getDivision() + "', " +
                        newEmployee.getSalary() + ")";
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) statement.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }


    @Override
    public void updateEmployee(Employee employee) {
        Statement statement = null;
        try {
            statement = connection.createStatement();
            String sql = "UPDATE employees SET " +
                    "first_name = '" + employee.getFirstName() + "', " +
                    "last_name = '" + employee.getLastName() + "', " +
                    "ssn = '" + employee.getSsn() + "', " +
                    "job_title = '" + employee.getJobTitle() + "', " +
                    "division = '" + employee.getDivision() + "', " +
                    "salary = " + employee.getSalary() +
                    " WHERE employee_id = " + employee.getEmployeeId();
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
}

    @Override
    public void deleteEmployee(int employeeId) {
        try {
            String query = "DELETE FROM employees WHERE employee_id = "+ employeeId;
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Employee getEmployeeById(int employeeId) {
        Employee employee = null;
        try {
            String query = "SELECT * FROM employees WHERE employee_id = " + employeeId;
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(query);

            if (result.next()) {
                employee = new Employee(
                    result.getInt("employee_id"),
                    result.getString("first_name"),
                    result.getString("last_name"),
                    result.getString("ssn"),
                    result.getString("job_title"),
                    result.getString("division"),
                    result.getDouble("salary")
                );
            }

            result.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employee;
    }

    @Override
    public List<Employee> searchEmployee(String jobTitle, String division) {
        List<Employee> employees = new ArrayList<>();
        try {
            String sql = "SELECT * FROM employees WHERE job_title = '" + jobTitle + "' AND division = '" + division + "'";
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
            while (rs.next()) {
                int id = result.getInt("employee_id");
                String firstName = result.getString("first_name");
                String lastName = result.getString("last_name");
                String ssn = result.getString("ssn");
                String title = result.getString("job_title");
                String div = result.getString("division");
                double salary = result.getDouble("salary");
                Employee emp = new Employee(id, firstName, lastName, ssn, title, div, salary);
                employees.add(emp);
            }
            result.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employees;
    }


    @Override
    public void updateSalaries(String division, Double percentIncrease) {
        try {
            Statement statement = connection.createStatement();
            double multiplier = 1 + percentIncrease / 100;
            String sql = "UPDATE employees SET salary = salary * " + multiplier +
                        " WHERE division = '" + division + "'";
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        } 
    }

}
