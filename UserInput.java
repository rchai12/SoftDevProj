import java.util.Scanner;
public class UserInput {
    ReportGenerator reportGenerator;

    public UserInput (ReportGenerator rg) {
        this.reportGenerator = rg;
    }

    public Employee getEmployeeInfo() {
        Scanner scan = new Scanner(System.in);

        System.out.println("Enter ID: ");
        int id = scan.nextInt();
        scan.nextLine();

        System.out.println("Enter First Name: ");
        String first_name = scan.nextLine();

        System.out.println("Enter Last Name: ");
        String last_name = scan.nextLine();

        System.out.println("Enter your SSN: ");
        String SSN = scan.nextLine();

        this.reportGenerator.generateEmployeeInfo(id, first_name, last_name, SSN);
    }

    public void getSearchParameters() {
        Scanner scan = new Scanner(System.in);

        System.out.println("Searching for User? Enter the employees full name, SSN, and / or Employee ID. Leave field blank to skip. \n Enter First Name: ");
        String first_name = scan.nextLine();

        System.out.println("Enter Last Name: ");
        String last_name = scan.nextLine();

        System.out.println("Enter the SSN: ");
        String SSN = scan.nextLine();

        System.out.println("Enter ID: ");
        int id = scan.nextInt();
        scan.nextLine();
    }

    public Employee updateEmployeeFromInput(Employee employee) {
        Scanner scan = new Scanner(System.in);
        Employee updatedEmployee = new Employee(employee.employeeId, employee.firstName, employee.lastName, employee.SSN, employee.division, employee.jobTitle, employee.salary);

        boolean done = false;
        while (!done) {
            System.out.println("\nSelect field to update:");
            System.out.println("1. First Name");
            System.out.println("2. Last Name");
            System.out.println("3. SSN");
            System.out.println("4. Job Title");
            System.out.println("5. Division");
            System.out.println("6. Salary");
            System.out.println("7. Done");

            System.out.print("Enter the number of the field you wish to change: ");
            int choice = Integer.parseInt(scan.nextLine());

            switch (choice) {
                case 1:
                    System.out.println("Enter the new First Name: ");
                    updatedEmployee.firstName = scan.nextLine();
                    break;
                case 2:
                    System.out.println("Enter the new Last Name: ");
                    updatedEmployee.lastName = scan.nextLine();
                    break;
                case 3:
                    System.out.println("Enter the new SSN: ");
                    updatedEmployee.SSN = scan.nextLine();
                    break;
                case 4:
                    System.out.println("Enter the new job title: ");
                    updatedEmployee.jobTitle = scan.nextLine();
                    break;
                case 5:
                    System.out.println("Enter the new Division: ");
                    updatedEmployee.division = scan.nextLine();
                    break;
                case 6:
                    System.out.println("Enter your new Salary: ");
                    updatedEmployee.salary = scan.nextDouble();
                    break;
                case 7:
                    done = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again!");
            }
        }
        return updatedEmployee;
    }
}