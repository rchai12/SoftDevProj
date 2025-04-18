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
                System.out.println("ID: " + emp.employeeId);
                System.out.println("Name: " + emp.firstName + " " + emp.lastName);
                System.out.println("SSN: " + emp.SSN);
                System.out.println("Job Title: " + emp.jobTitle);
                System.out.println("Division: " + emp.division);
                System.out.println("Salary: $" + emp.salary);
            }
        }
        return employees;
    }

    public void addEmployeeInfo(Employee newEmployee) {
        newEmployee = db.addEmployee(newEmployee);
        System.out.println("Added the employee with info");
        System.out.println("-----------------------------------");
        System.out.println("ID: " + newEmployee.employeeId);
        System.out.println("Name: " + newEmployee.firstName + " " + newEmployee.lastName);
        System.out.println("SSN: " + newEmployee.SSN);
        System.out.println("Job Title: " + newEmployee.jobTitle);
        System.out.println("Division: " + newEmployee.division);
        System.out.println("Salary: $" + newEmployee.salary);
    }

    public void updatedEmployeeInfo(Employee updatedEmployee) {
        db.updateEmployee(updatedEmployee);
        System.out.println("Updated the employee with info");
        System.out.println("-----------------------------------");
        System.out.println("ID: " + updatedEmployee.employeeId);
        System.out.println("Name: " + updatedEmployee.firstName + " " + updatedEmployee.lastName);
        System.out.println("SSN: " + updatedEmployee.SSN);
        System.out.println("Job Title: " + updatedEmployee.jobTitle);
        System.out.println("Division: " + updatedEmployee.division);
        System.out.println("Salary: $" + updatedEmployee.salary);
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