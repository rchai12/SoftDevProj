import java.util.List;

public class ReportGenerator {
    // Reference to the database interface to fetch employee data
    private EmployeeDatabaseInterface db;

    // Constructor takes any implementation of EmployeeDatabaseInterface
    public ReportGenerator(EmployeeDatabaseInterface db) {
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
            List<Employee> employees = db.searchEmployee(jobTitle, "%"); // '%' is SQL wildcard

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
            List<Employee> employees = db.searchEmployee("%", div); // '%' is SQL wildcard

            double total = 0;
            for (Employee emp : employees) {
                total += emp.getSalary(); // Sum their salaries
            }

            System.out.println(div + ": $" + total);
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