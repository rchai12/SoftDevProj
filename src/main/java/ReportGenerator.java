import java.util.ArrayList;
import java.util.List;

public class ReportGenerator {
    // Reference to the database interface to fetch employee data
    private EmployeeDatabaseImplementation db;

    // Constructor takes any implementation of EmployeeDatabaseInterface
    public ReportGenerator(EmployeeDatabaseImplementation db) {
        this.db = db;
    }

    // Generates a simple pay history for a single employee
    public void generateSalaryHistory(int employeeId) {
        Employee emp = db.getEmployeeById(employeeId); // Fetch employee info
        if (emp != null) {
            System.out.println("=== Salary History for " + emp.getFullName() + " ===");
            System.out.println("Job Title: " + emp.getJobTitle());
            System.out.println("Division: " + emp.getDivision());
            System.out.println("Current Salary: $" + emp.getSalary());
            System.out.println("(Salary history details would be listed here if stored separately)");
        } else {
            System.out.println("Employee not found.");
        }
    }

    // Generates total salary report grouped by job title for a given month
    public void generateSalaryByJobTitle(String month) {
        System.out.println("=== Salary by Job Title for " + month + " ===");

        // Hardcoded job titles to simulate a real report
        String[] jobTitles = {
            "Sales Representative",
            "Account Executive",
            "Marketing Specialist",
            "PR Representative",
            "Software Engineer",
            "Data Analyst",
            "QA Engineer",
            "IT Support Technician",
            "System Administrator",
            "Financial Analyst",
            "Accountant",
            "Auditor",
            "HR Specialist",
            "Recruiter",
            "Manager",
            "Office Manager",
            "Legal Counsel",
            "Compliance Officer",
            "Operations Manager",
            "Customer Support Rep"
        };

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
    public void generateSalaryByDivision(String month) {
        System.out.println("=== Salary by Division for " + month + " ===");

        // Hardcoded divisions
        String[] divisions = {
            "Engineering",
            "Sales",
            "Human Resources",
            "Finance",
            "Marketing",
            "Information Technology",
            "Administration",
            "Legal",
            "Operations",
            "Customer Support"
        };

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

    public List<Employee> generateEmployeeInfo(Integer id, String first_name, String last_name, String SSN) {
        List<Employee> employees = db.searchEmployee(
            id != null && id > 0 ? id : null,
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

    public void updateDivisionSalaries(String division, double percentIncrease, Double minimum) {
        List<Employee> employees = db.updateDivisionSalaries(division, percentIncrease, minimum);
        System.out.println("Updated these employees in the " + division + " Division");
        for (Employee emp : employees) {
                System.out.println("-----------------------------------");
                printEmployeeInfo(emp);
        }
    }

    public void updateJobTitleSalaries(String jobTitle, double percentIncrease, Double minimum) {
        List<Employee> employees = db.updateJobTitleSalaries(jobTitle, percentIncrease, minimum);
        System.out.println("Updated these employees with the Job Title " + jobTitle);
        for (Employee emp : employees) {
                System.out.println("-----------------------------------");
                printEmployeeInfo(emp);
        }
    }

    public void updateAllSalaries(double percentIncrease, Double minimum) {
        List<Employee> employees = db.updateAllSalaries(percentIncrease, minimum);
        System.out.println("Updated these employee salaries");
        for (Employee emp : employees) {
                System.out.println("-----------------------------------");
                printEmployeeInfo(emp);
        }
    }

    // Method to print a formatted version of the pay statement
    public void printPayStatement(PayStatement payStatement) {
        System.out.println("========== PAY STATEMENT ==========");
        System.out.println("Payment ID   : " + payStatement.getPaymentId());
        System.out.println("Employee ID  : " + payStatement.getEmployeeId());
        System.out.println("Payment Date : " + payStatement.getPaymentDate());
        System.out.println("Amount Paid  : $" + String.format("%.2f", payStatement.getAmount()));
        System.out.println("===================================");
    }

    public void generatePayStatement(Employee employee, String date, double bonus) {
        PayStatement payStatement = new PayStatement(employee.getEmployeeId(), date, employee.getSalary() + bonus);
        payStatement = db.addPayStatement(payStatement);
        printPayStatement(payStatement);
    }

    public void retrievePayStatementsByDate(String date) {
        List<PayStatement> payStatements = db.searchPayStatementsByDate(date);
        for (PayStatement ps: payStatements) {
            printPayStatement(ps);
        }
    }

    public void retrievePayStatementsByEmployees(List<Integer> employeeIds, String date) {
        List<PayStatement> payStatements = db.searchPayStatementsByEmployees(employeeIds, date);
        for (PayStatement ps: payStatements) {
            printPayStatement(ps);
        }
    }

    public void retrievePayStatementsByDivision(String division, String date) {
        List<Employee> employees = db.searchByDivisionAndTitle("%", division);
        List<Integer> employeeIds = new ArrayList<>();
        for (Employee e : employees) {
            employeeIds.add(e.getEmployeeId());
        }
        retrievePayStatementsByEmployees(employeeIds, date);
    }

    public void retrievePayStatementsByJobTitle(String jobTitle, String date) {
        List<Employee> employees = db.searchByDivisionAndTitle(jobTitle, "%");
        List<Integer> employeeIds = new ArrayList<>();
        for (Employee e : employees) {
            employeeIds.add(e.getEmployeeId());
        }
        retrievePayStatementsByEmployees(employeeIds, date);
    }

    public void generatePayStatements(String date, double bonus) {
        List<Employee> employees = db.searchEmployee(null, null, null, null);
        for (Employee e : employees) {
            generatePayStatement(e, date, bonus);
        }
    }

    public void generatePayStatementsByDivision(String division, String date, double bonus) {
        List<Employee> employees = db.searchByDivisionAndTitle("%", division);
        for (Employee e : employees) {
            generatePayStatement(e, date, bonus);
        }
    }

    public void generatePayStatementsByJobTitle(String jobTitle, String date, double bonus) {
        List<Employee> employees = db.searchByDivisionAndTitle(jobTitle, "%");
        for (Employee e : employees) {
            generatePayStatement(e, date, bonus);
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