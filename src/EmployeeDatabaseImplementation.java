import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDatabaseImplementation implements EmployeeDatabaseInterface {

    private Connection connection;

    public EmployeeDatabaseImplementation(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Employee addEmployee(Employee newEmployee) {
        PreparedStatement statement = null;
        ResultSet generatedKeys = null;
        try {
            String sql = "INSERT INTO employees (first_name, last_name, ssn, job_title, division, salary) " +
                        "VALUES (?, ?, ?, ?, ?, ?)";

            statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, newEmployee.getFirstName());
            statement.setString(2, newEmployee.getLastName());
            statement.setString(3, newEmployee.getSSN());
            statement.setString(4, newEmployee.getJobTitle());
            statement.setString(5, newEmployee.getDivision());
            statement.setDouble(6, newEmployee.getSalary());

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Adding employee failed, no rows affected.");
            }
            generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                int generatedId = generatedKeys.getInt(1);
                newEmployee.setEmployeeId(generatedId);
                //System.out.println("Employee added with ID: " + generatedId);
            } else {
                throw new SQLException("Adding employee failed, no ID obtained.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (generatedKeys != null) generatedKeys.close();
                if (statement != null) statement.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return newEmployee;
    }

    @Override
    public void updateEmployee(Employee employee) {
        Statement statement = null;
        try {
            statement = connection.createStatement();
            String sql = "UPDATE employees SET " +
                    "first_name = '" + employee.getFirstName() + "', " +
                    "last_name = '" + employee.getLastName() + "', " +
                    "ssn = '" + employee.getSSN() + "', " +
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
    public List<Employee> searchByDivisionAndTitle(String jobTitle, String division) {
        List<Employee> employees = new ArrayList<>();
        try {
            String sql = "SELECT * FROM employees WHERE job_title = '" + jobTitle + "' AND division = '" + division + "'";
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
            while (result.next()) {
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

    public List<Employee> searchEmployee(Integer id, String ssn, String firstName, String lastName) {
        List<Employee> employees = new ArrayList<>();
        PreparedStatement statement = null;
        ResultSet result = null;
        try {
            StringBuilder query = new StringBuilder("SELECT * FROM employees WHERE 1=1");
            List<Object> parameters = new ArrayList<>();
            if (id != null) {
                query.append(" AND employee_id = ?");
                parameters.add(id);
            }
            if (ssn != null && !ssn.isEmpty()) {
                query.append(" AND ssn = ?");
                parameters.add(ssn);
            }
            if (firstName != null && !firstName.isEmpty()) {
                query.append(" AND first_name = ?");
                parameters.add(firstName);
            }
            if (lastName != null && !lastName.isEmpty()) {
                query.append(" AND last_name = ?");
                parameters.add(lastName);
            }
            statement = connection.prepareStatement(query.toString());
            for (int i = 0; i < parameters.size(); i++) {
                statement.setObject(i + 1, parameters.get(i));
            }
            result = statement.executeQuery();
            while (result.next()) {
                Employee employee = new Employee(
                    result.getInt("employee_id"),
                    result.getString("first_name"),
                    result.getString("last_name"),
                    result.getString("ssn"),
                    result.getString("job_title"),
                    result.getString("division"),
                    result.getDouble("salary")
                );
                employees.add(employee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (result != null) result.close();
                if (statement != null) statement.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return employees;
    }

}
