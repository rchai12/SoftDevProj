import java.util.List;
import java.util.Scanner;

public class UserInput {
    ReportGenerator reportGenerator;

    public UserInput (ReportGenerator rg) {
        this.reportGenerator = rg;
    }

    public Employee addEmployeeInfo() {
        Scanner scan = new Scanner(System.in);

        System.out.println("Enter Employee's First Name: ");
        String first_name = scan.nextLine();

        System.out.println("Enter Employee's Last Name: ");
        String last_name = scan.nextLine();

        System.out.println("Enter Employee's SSN: ");
        String SSN = scan.nextLine();

        System.out.println("Enter Employee's Job Title: ");
        String job_title = scan.nextLine();

        System.out.println("Entre Employee's division: ");
        String division = scan.nextLine();

        System.out.println("Enter Employee's monthly salary: ");
        double salary = scan.nextDouble();

        Employee newEmployee = new Employee(first_name, last_name, SSN, job_title, division, salary);

        this.reportGenerator.addEmployeeInfo(newEmployee);
        return newEmployee;
    }

    public List<Employee> getSearchParameters() {
        Scanner scan = new Scanner(System.in);

        System.out.println("Searching for User? Enter the employees full name, SSN, and / or Employee ID. Leave field blank to skip. \n Enter First Name: ");
        String first_name = scan.nextLine();

        System.out.println("Enter Last Name: ");
        String last_name = scan.nextLine();

        System.out.println("Enter the SSN: ");
        String SSN = scan.nextLine();

        System.out.println("Enter ID: ");
        int id = scan.nextInt();
        List<Employee> employees = this.reportGenerator.generateEmployeeInfo(id, first_name, last_name, SSN);
        return employees;
    }

    public Employee updateEmployeeFromInput(Employee employee) {
        Scanner scan = new Scanner(System.in);

        System.out.println("\n--- Update Employee Information ---");
        System.out.println("(Leave any field blank to keep current value)");

        System.out.print("Current First Name [" + employee.firstName + "], New First Name: ");
        String firstName = scan.nextLine();
        if (!firstName.trim().isEmpty()) {
            employee.firstName = firstName;
        }

        System.out.print("Current Last Name [" + employee.lastName + "], New Last name: ");
        String lastName = scan.nextLine();
        if (!lastName.trim().isEmpty()) {
            employee.lastName = lastName;
        }

        System.out.print("Current SSN [" + employee.SSN + "], New SSN: ");
        String ssn = scan.nextLine();
        if (!ssn.trim().isEmpty()) {
            employee.SSN = ssn;
        }

        System.out.print("Current Job Title [" + employee.jobTitle + "], New Job Title: ");
        String jobTitle = scan.nextLine();
        if (!jobTitle.trim().isEmpty()) {
            employee.jobTitle = jobTitle;
        }

        System.out.print("Current Division [" + employee.division + "], New Division: ");
        String division = scan.nextLine();
        if (!division.trim().isEmpty()) {
            employee.division = division;
        }

        System.out.print("Current Salary [" + employee.salary + "], New Salary: ");
        String salaryInput = scan.nextLine();
        if (!salaryInput.trim().isEmpty()) {
            try {
                double newSalary = Double.parseDouble(salaryInput);
                employee.salary = newSalary;
            } catch (NumberFormatException e) {
                System.out.println("Invalid salary input. Salary not updated.");
            }
        }

        this.reportGenerator.updatedEmployeeInfo(employee);
    }

    public Employee selectEmployee(List<Employee> employees) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Please enter the ID of the employee you would like to select: ");
        Integer id = scan.nextInt();
        for (Employee emp : employees) {
            if (emp.getEmployeeId() == id) {
                System.out.println("Employee selected: " + emp.getFullName());
                return emp;
            }
        }
        System.out.println("No employee found with ID: " + id);
        return null;
    }

    public void employeeActions(Employee employee) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Select one of the options:");
        System.out.println("1 - Generate Employee's Pay History");
        System.out.println("2 - Update Employee's Info");
        System.out.println("3 - Update Employee's Salary");
        System.out.println("4 - Go back to previous menu");
        int choice = scan.nextInt();
        switch (choice) {
            case 1: reportGenerator.generatePayHistory(employee.getEmployeeId()); break;
            case 2: employee = updateEmployeeFromInput(employee); break;
            case 3: employee = updateEmployeeSalary(employee); break;
            default: 
        }
    }

    public Employee updateEmployeeSalary(Employee employee) {
        Scanner scan = new Scanner(System.in);
        System.out.println("What p");
    }

    public void mainMenu() {
        /*selection menu, search employee info? update employee? well you need to first search the employee then select the employee
        print pay statement for employee? well first you need to search the employee then select the employee
        add an employee?
        generate pay statements by divison or title?
        etc,*/
    }

}