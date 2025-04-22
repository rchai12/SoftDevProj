import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
    public Employee updateEmployee(Employee employee) {
        PreparedStatement updateStmt = null;
        PreparedStatement selectStmt = null;
        ResultSet resultSet = null;
        Employee updatedEmployee = null;

        try {
            String updateQuery = "UPDATE employees SET first_name = ?, last_name = ?, ssn = ?, job_title = ?, division = ?, salary = ? WHERE employee_id = ?";
            updateStmt = connection.prepareStatement(updateQuery);
            updateStmt.setString(1, employee.getFirstName());
            updateStmt.setString(2, employee.getLastName());
            updateStmt.setString(3, employee.getSSN());
            updateStmt.setString(4, employee.getJobTitle());
            updateStmt.setString(5, employee.getDivision());
            updateStmt.setDouble(6, employee.getSalary());
            updateStmt.setInt(7, employee.getEmployeeId());
            updateStmt.executeUpdate();

            String selectQuery = "SELECT * FROM employees WHERE employee_id = ?";
            selectStmt = connection.prepareStatement(selectQuery);
            selectStmt.setInt(1, employee.getEmployeeId());
            resultSet = selectStmt.executeQuery();

            if (resultSet.next()) {
                updatedEmployee = new Employee(
                    resultSet.getInt("employee_id"),
                    resultSet.getString("first_name"),
                    resultSet.getString("last_name"),
                    resultSet.getString("ssn"),
                    resultSet.getString("job_title"),
                    resultSet.getString("division"),
                    resultSet.getDouble("salary")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (updateStmt != null) updateStmt.close();
                if (selectStmt != null) selectStmt.close();
                if (resultSet != null) resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return updatedEmployee;
    }


    @Override
    public Employee deleteEmployee(int employeeId) {
        Employee employee = null;
        try {
            String selectQuery = "SELECT * FROM employees WHERE employee_id = " + employeeId;
            Statement selectStmt = connection.createStatement();
            ResultSet resultSet = selectStmt.executeQuery(selectQuery);

            if (resultSet.next()) {
                employee = new Employee(
                    resultSet.getInt("employee_id"),
                    resultSet.getString("first_name"),
                    resultSet.getString("last_name"),
                    resultSet.getString("ssn"),
                    resultSet.getString("job_title"),
                    resultSet.getString("division"),
                    resultSet.getDouble("salary")
                );
            }
            resultSet.close();
            selectStmt.close();

            String deleteQuery = "DELETE FROM employees WHERE employee_id = " + employeeId;
            Statement deleteStmt = connection.createStatement();
            deleteStmt.executeUpdate(deleteQuery);
            deleteStmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employee;
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
            String sql = "SELECT * FROM employees WHERE job_title = '" + jobTitle + "' OR division = '" + division + "'";
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
    public List<Employee> updateDivisionSalaries(String division, Double percentIncrease, Double minimum) {
        List<Employee> updatedEmployees = new ArrayList<>();
        double multiplier = 1 + percentIncrease / 100;
        try {
            Statement statement = connection.createStatement();
            String condition = "division = '" + division + "'";
            if (minimum != null) {
                condition += " AND salary < " + minimum;
            }
            String updateSql = "UPDATE employees SET salary = salary * " + multiplier + " WHERE " + condition;
            statement.executeUpdate(updateSql);
            String selectSql = "SELECT * FROM employees WHERE " + condition;
            ResultSet resultSet = statement.executeQuery(selectSql);

            while (resultSet.next()) {
                Employee emp = new Employee(
                    resultSet.getInt("employee_id"),
                    resultSet.getString("first_name"),
                    resultSet.getString("last_name"),
                    resultSet.getString("ssn"),
                    resultSet.getString("job_title"),
                    resultSet.getString("division"),
                    resultSet.getDouble("salary")
                );
                updatedEmployees.add(emp);
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return updatedEmployees;
    }

    @Override
    public List<Employee> updateJobTitleSalaries(String jobTitle, Double percentIncrease, Double minimum) {
        List<Employee> updatedEmployees = new ArrayList<>();
        double multiplier = 1 + percentIncrease / 100;
        try {
            Statement statement = connection.createStatement();
            String condition = "job_title = '" + jobTitle + "'";
            if (minimum != null) {
                condition += " AND salary < " + minimum;
            }
            String updateSql = "UPDATE employees SET salary = salary * " + multiplier + " WHERE " + condition;
            statement.executeUpdate(updateSql);
            String selectSql = "SELECT * FROM employees WHERE " + condition;
            ResultSet resultSet = statement.executeQuery(selectSql);
            while (resultSet.next()) {
                Employee emp = new Employee(
                    resultSet.getInt("employee_id"),
                    resultSet.getString("first_name"),
                    resultSet.getString("last_name"),
                    resultSet.getString("ssn"),
                    resultSet.getString("job_title"),
                    resultSet.getString("division"),
                    resultSet.getDouble("salary")
                );
                updatedEmployees.add(emp);
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return updatedEmployees;
    }

    @Override
    public List<Employee> updateAllSalaries(Double percentIncrease, Double minimum) {
        List<Employee> updatedEmployees = new ArrayList<>();
        double multiplier = 1 + percentIncrease / 100;
        try {
            Statement statement = connection.createStatement();
            String condition = "1=1";
            if (minimum != null) {
                condition = "salary < " + minimum;
            }
            String updateSql = "UPDATE employees SET salary = salary * " + multiplier + " WHERE " + condition;
            statement.executeUpdate(updateSql);
            String selectSql = "SELECT * FROM employees WHERE " + condition;
            ResultSet resultSet = statement.executeQuery(selectSql);
            while (resultSet.next()) {
                Employee emp = new Employee(
                    resultSet.getInt("employee_id"),
                    resultSet.getString("first_name"),
                    resultSet.getString("last_name"),
                    resultSet.getString("ssn"),
                    resultSet.getString("job_title"),
                    resultSet.getString("division"),
                    resultSet.getDouble("salary")
                );
                updatedEmployees.add(emp);
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return updatedEmployees;
    }

    @Override
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

    public PayStatement addPayStatement(PayStatement payStatement) {
        String sql = "INSERT INTO paystatements (employee_id, payment_date, amount) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, payStatement.getEmployeeId());
            stmt.setString(2, payStatement.getPaymentDate());
            stmt.setDouble(3, payStatement.getAmount());
            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int generatedId = generatedKeys.getInt(1);
                        return new PayStatement(generatedId,
                                                payStatement.getEmployeeId(),
                                                payStatement.getPaymentDate(),
                                                payStatement.getAmount());
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Failed to insert pay statement: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
    
    public List<PayStatement> searchPayStatementsByEmployees(List<Integer> employeeIds, String date) {
        List<PayStatement> result = new ArrayList<>();
        if (employeeIds == null || employeeIds.isEmpty() || date == null || date.isEmpty()) {
            return result;
        }
        String placeholders = String.join(",", java.util.Collections.nCopies(employeeIds.size(), "?"));
        String sql = "SELECT payment_id, employee_id, payment_date, amount FROM paystatements " +
                     "WHERE employee_id IN (" + placeholders + ") AND payment_date = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            int i = 0;
            for (; i < employeeIds.size(); i++) {
                stmt.setInt(i + 1, employeeIds.get(i));
            }
            stmt.setString(i + 1, date);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int paymentId = rs.getInt("payment_id");
                    int employeeId = rs.getInt("employee_id");
                    String paymentDate = rs.getString("payment_date");
                    double amount = rs.getDouble("amount");
                    PayStatement ps = new PayStatement(paymentId, employeeId, paymentDate, amount);
                    result.add(ps);
                }
            }
        } catch (SQLException e) {
            System.err.println("Failed to search pay statements by employees and date: " + e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    public List<PayStatement> searchPayStatementsByDate(String date) {
        List<PayStatement> result = new ArrayList<>();
        String sql = "SELECT payment_id, employee_id, payment_date, amount FROM paystatements WHERE payment_date = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, date);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int paymentId = rs.getInt("payment_id");
                    int employeeId = rs.getInt("employee_id");
                    String paymentDate = rs.getString("payment_date");
                    double amount = rs.getDouble("amount");
                    PayStatement ps = new PayStatement(paymentId, employeeId, paymentDate, amount);
                    result.add(ps);
                }
            }
        } catch (SQLException e) {
            System.err.println("Failed to search pay statements by date: " + e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

}
