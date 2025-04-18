import java.util.List;

public interface EmployeeDatabaseInterface {

    Employee addEmployee(Employee newEmployee);

    void updateEmployee(Employee employee);

    void deleteEmployee(int employeeId);

    Employee getEmployeeById(int employeeId);

    List<Employee> searchByDivisionAndTitle(String jobTitle, String division);

    void updateSalaries(String division, Double percentIncrease);

    List<Employee> searchEmployee(Integer id, String ssn, String firstName, String lastName);
}
