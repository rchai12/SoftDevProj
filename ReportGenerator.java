import java.util.List;

public class ReportGenerator {
    // Reference to the database interface to fetch employee data
    private EmployeeDatabaseImplementation db;

    // Constructor takes any implementation of EmployeeDatabaseInterface
    public ReportGenerator(EmployeeDatabaseImplementation db) {
        this.db = db;
    }

    // Generates a simple pay history for a single employee
    public void generatePayHistory(int employeeId) {
        Employee emp = db.getEmployeeById(employeeId); // Fetch employee info
        if (emp != null) {
            System.out.println("=== Pay History for " + emp.getFullName() + " ===");
            System.out.println("Job Title: " + emp.getJobTitle());
            System.out.println("Division: " + emp.getDivision());
            System.out.println("Current Salary: $" + emp.getSalary());
            System.out.println("(Pay history details would be listed here if stored separately)");
        } else {
            System.out.println("Employee not found.");
        }
    }

    // Generates total salary report grouped by job title for a given month
    public void generatePayByJobTitle(String month) {
        System.out.println("=== Pay by Job Title for " + month + " ===");

        // Hardcoded job titles to simulate a real report
        String[] jobTitles = {"Engineer", "Manager", "HR"};

        for (String jobTitle : jobTitles) {
            // Fetch all employees with this job title across all divisions
            List<Employee> employees = db.searchByDivisionAndTitle(jobTitle, "%"); // '%' is SQL wildcard

            double total = 0;
            for (Employee emp : employees) {
                total += emp.getSalary(); // Sum their salaries
            }

            System.out.println(jobTitle + ": $" + total);
        }
    }

    // Generates total salary report grouped by division for a given month
    public void generatePayByDivision(String month) {
        System.out.println("=== Pay by Division for " + month + " ===");

        // Hardcoded divisions
        String[] divisions = {"Tech", "HR", "Marketing"};

        for (String div : divisions) {
            // Fetch all employees with any job title in this division
            List<Employee> employees = db.searchByDivisionAndTitle("%", div); // '%' is SQL wildcard

            double total = 0;
            for (Employee emp : employees) {
                total += emp.getSalary(); // Sum their salaries
            }

            System.out.println(div + ": $" + total);
        }
    }

    public List<Employee> generateEmployeeInfo(int id, String first_name, String last_name, String SSN) {
        List<Employee> employees = db.searchEmployee(
            id > 0 ? id : null,
            (SSN != null && !SSN.isEmpty()) ? SSN : null,
            (first_name != null && !first_name.isEmpty()) ? first_name : null,
            (last_name != null && !last_name.isEmpty()) ? last_name : null
        );
        if (employees.isEmpty()) {
            System.out.println("No employees found with the given information.");
        } else {
            System.out.println("Matching Employees:");
            for (Employee emp : employees) {
                System.out.println("-----------------------------------");
                printEmployeeInfo(emp);
            }
        }
        return employees;
    }

    public Employee addEmployeeInfo(Employee newEmployee) {
        newEmployee = db.addEmployee(newEmployee);
        System.out.println("Added the employee with info");
        System.out.println("-----------------------------------");
        printEmployeeInfo(newEmployee);
        return newEmployee;
    }

    public Employee updatedEmployeeInfo(Employee updatedEmployee) {
        updatedEmployee = db.updateEmployee(updatedEmployee);
        System.out.println("Updated the employee with info");
        System.out.println("-----------------------------------");
        printEmployeeInfo(updatedEmployee);
        return updatedEmployee;
    }

    public Employee deletedEmployee(Employee deletedEmployee) {
        deletedEmployee = db.deleteEmployee(deletedEmployee.employeeId);
        System.out.println("Deleted the employee  with info");
        System.out.println("-----------------------------------");
        printEmployeeInfo(deletedEmployee);
        return deletedEmployee;
    }

    public void printEmployeeInfo(Employee employee) {
        System.out.println("ID: " + employee.getEmployeeId());
        System.out.println("Name: " + employee.getFullName());
        System.out.println("SSN: " + employee.getSSN());
        System.out.println("Job Title: " + employee.getJobTitle());
        System.out.println("Division: " + employee.getDivision());
        System.out.println("Salary: $" + employee.getSalary());
    }

    public void updateDivisionSalaries(String division, double percentIncrease, double minimum) {
        List<Employee> employees = db.updateDivisionSalaries(division, percentIncrease, minimum);
        System.out.println("Updated these employees in the " + division + " Division");
        for (Employee emp : employees) {
                System.out.println("-----------------------------------");
                printEmployeeInfo(emp);
        }
    }

    public void updateJobTitleSalaries(String jobTitle, double percentIncrease, double minimum) {
        List<Employee> employees = db.updateJobTitleSalaries(jobTitle, percentIncrease, minimum);
        System.out.println("Updated these employees with the Job Title " + jobTitle);
        for (Employee emp : employees) {
                System.out.println("-----------------------------------");
                printEmployeeInfo(emp);
        }
    }

    public void updateAllSalaries(double percentIncrease, double minimum) {
        List<Employee> employees = db.updateAllSalaries(percentIncrease, minimum);
        System.out.println("Updated these employee salaries");
        for (Employee emp : employees) {
                System.out.println("-----------------------------------");
                printEmployeeInfo(emp);
        }
    }
}


/*

 * How to Call:
Connection conn = DriverManager.getConnection(...); // Set this up correctly
EmployeeDatabaseInterface db = new EmployeeDatabaseImplementation(conn);
ReportGenerator generator = new ReportGenerator(db);

generator.generatePayHistory(101);
generator.generatePayByJobTitle("April");
generator.generatePayByDivision("April");


 * Example Output:
=== Pay by Job Title for April ===
Engineer: $145000.0
Manager: $210000.0
HR: $82000.0

=== Pay by Division for April ===
Tech: $305000.0
HR: $82000.0
Marketing: $120000.0

 */