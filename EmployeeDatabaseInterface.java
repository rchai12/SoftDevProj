import java.util.List;

public interface EmployeeDatabaseInterface {

    void addEmployee(Employee newEmployee);

    void updateEmployee(Employee employee);

    void deleteEmployee(int employeeId);

    Employee getEmployeeById(int employeeId);

    List<Employee> searchEmployee(String jobTitle, String division);

    void updateSalaries(String division, Double percentIncrease);
}
