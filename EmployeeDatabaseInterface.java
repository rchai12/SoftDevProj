import java.util.List;

public interface EmployeeDatabaseInterface {

    Employee addEmployee(Employee newEmployee);

    Employee updateEmployee(Employee employee);

    Employee deleteEmployee(int employeeId);

    Employee getEmployeeById(int employeeId);

    List<Employee> searchByDivisionAndTitle(String jobTitle, String division);

    List<Employee> updateDivisionSalaries(String division, Double percentIncrease, Double minimum);

    List<Employee> updateJobTitleSalaries(String jobTitle, Double percentIncrease, Double minimum);

    List<Employee> updateAllSalaries(Double percentIncrease, Double minimum);

    List<Employee> searchEmployee(Integer id, String ssn, String firstName, String lastName);
}
