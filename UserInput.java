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
        String job_title = selectJobTitle();

        System.out.println("Entre Employee's division: ");
        String division = selectDivision();

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
        System.out.println("(Leave any field blank to keep current value, except for jobtitle and division)");

        System.out.print("Current First Name [" + employee.getFirstName() + "], New First Name: ");
        String firstName = scan.nextLine();
        if (!firstName.trim().isEmpty()) {
            employee.setFirstName(firstName);
        }

        System.out.print("Current Last Name [" + employee.getLastName() + "], New Last name: ");
        String lastName = scan.nextLine();
        if (!lastName.trim().isEmpty()) {
            employee.setLastName(lastName);
        }

        System.out.print("Current SSN [" + employee.getSSN() + "], New SSN: ");
        String ssn = scan.nextLine();
        if (!ssn.trim().isEmpty()) {
            employee.setSSN(ssn);
        }

        System.out.print("Current Job Title [" + employee.getJobTitle() + "], New Job Title: ");
        String jobTitle = selectJobTitle();
        employee.setJobTitle(jobTitle);

        System.out.print("Current Division [" + employee.getDivision() + "], New Division: ");
        String division = selectDivision();
        employee.setDivision(division);

        System.out.print("Current Salary [" + employee.getSalary() + "], New Salary: ");
        String salaryInput = scan.nextLine();
        if (!salaryInput.trim().isEmpty()) {
            try {
                double newSalary = Double.parseDouble(salaryInput);
                employee.setSalary(newSalary);
            } catch (NumberFormatException e) {
                System.out.println("Invalid salary input. Salary not updated.");
            }
        }

        employee = this.reportGenerator.updatedEmployeeInfo(employee);
        return employee;
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
        int choice;
        do {
            System.out.println("\nEmployee ID: " + employee.getEmployeeId() +" Selected Employee: " + employee.getFullName());
            System.out.println("Select one of the options:");
            System.out.println("1 - Generate Employee's Pay History");
            System.out.println("2 - Update Employee's Info");
            System.out.println("3 - Update Employee's Salary");
            System.out.println("4 - Delete the selected Employee");
            System.out.println("5 - Go back to previous menu");
            System.out.print("Enter your choice (1 - 5): ");
            while (!scan.hasNextInt()) {
                System.out.print("Invalid input. Please enter a number between 1 and 5: ");
                scan.next();
            }
            choice = scan.nextInt();
            switch (choice) {
                case 1: reportGenerator.generatePayHistory(employee.getEmployeeId()); break;
                case 2: employee = updateEmployeeFromInput(employee); break;
                case 3: employee = updateEmployeeSalary(employee); break;
                case 4: System.out.print("Are you sure you want to delete this employee? (y/n): ");
                        String confirm = scan.nextLine().trim().toLowerCase();
                        if (confirm.equals("y")) {
                            employee = reportGenerator.deletedEmployee(employee);
                            System.out.println("Employee deleted.");
                            return;
                        } else {
                            System.out.println("Deletion canceled.");
                        }
                        break;
                case 5: System.out.println("Returning to previous menu..."); break;
                default: System.out.println("Invalid choice. Please enter a number between 1 and 5.");
             }
        } while (choice != 5);
    }

    public Employee updateEmployeeSalary(Employee employee) {
        Scanner scan = new Scanner(System.in);
        System.out.println("What is the salary percentage increase for the employee");
        double increase = scan.nextDouble();
        employee.increaseSalary(increase / 100);
        employee = this.reportGenerator.updatedEmployeeInfo(employee);
        return employee;
    }

    public void mainMenu() {
        Scanner scan = new Scanner(System.in);
        int choice;
        do {
            System.out.println("Select one of the options:");
            System.out.println("1 - Search an Employee and perform an Employee action");
            System.out.println("2 - Add an Employee");
            System.out.println("3 - Generate Pay History by Employee Job Titles");
            System.out.println("4 - Generate Pay History by Employee Divisions");
            System.out.println("5 - Update a group of Employee Salaries");
            System.out.println("6 - Exit the program");
            System.out.print("Enter your choice (1 - 6): ");
            while (!scan.hasNextInt()) {
                System.out.print("Invalid input. Please enter a number between 1 and 5: ");
                scan.next();
            }
            choice = scan.nextInt();
            switch (choice) {
                case 1: handleEmployee(); break;
                case 2: addEmployeeInfo(); break;
                case 3: reportGenerator.generatePayByJobTitle(getMonth()); break;
                case 4: reportGenerator.generatePayByDivision(getMonth());; break;
                case 5: handleSalaryUpdate(); break;
                case 6: System.out.println("Exiting the program. Goodbye!"); break;
                default: System.out.println("Invalid choice. Please enter a number between 1 and 5.");
            }
        } while (choice != 6);
    }

    public void handleEmployee() {
        List<Employee> employees = getSearchParameters();
        Employee employee = selectEmployee(employees);
        employeeActions(employee);
    }

    public String getMonth() {
        String month = "";
        Scanner scan = new Scanner(System.in);
        int choice;
        do {
            System.out.println("Please enter the month you would like to search for: ");
            System.out.println("1 - January");
            System.out.println("2 - February");
            System.out.println("3 - March");
            System.out.println("4 - April");
            System.out.println("5 - May");
            System.out.println("6 - June");
            System.out.println("7 - July");
            System.out.println("8 - August");
            System.out.println("9 - September");
            System.out.println("10 - October");
            System.out.println("11 - November");
            System.out.println("12 - December");
            System.out.print("Enter your choice (1-12): ");
            while (!scan.hasNextInt()) {
                System.out.print("Invalid input. Please enter a number between 1 and 12: ");
                scan.next();
            }
            choice = scan.nextInt();
            switch (choice) {
                case 1: month = "January"; break;
                case 2: month = "February"; break;
                case 3: month = "March"; break;
                case 4: month = "April"; break;
                case 5: month = "May"; break;
                case 6: month = "June"; break;
                case 7: month = "July"; break;
                case 8: month = "August"; break;
                case 9: month = "September"; break;
                case 10: month = "October"; break;
                case 11: month = "November"; break;
                case 12: month = "December"; break;
                default: System.out.println("Invalid choice. Please enter a number between 1 and 12."); break;
            }
        } while (choice < 1 || choice > 12);
        return month;
    }

    public void handleSalaryUpdate() {
        Scanner scan = new Scanner(System.in);
        int choice;
        do {
            System.out.println("Select one of the options:");
            System.out.println("1 - Increase all employee salaries, optional minimum salary threshhold");
            System.out.println("2 - Increase all employee salaries in a division, optional minimum salary threshhold");
            System.out.println("3 - Increase all employee salaries of a specific job title, optional minimum salary threshhold");
            while (!scan.hasNextInt()) {
                System.out.print("Invalid input. Please enter a number between 1 and 12: ");
                scan.next();
            }
            choice = scan.nextInt();
            switch (choice) {
                case 1: increaseAllSalaries(); break;
                case 2: increaseSalaryByDivision(); break;
                case 3: increaseSalaryByJobTitle(); break;
                case 4: System.out.println("Returning to previous menu..."); break;
                default: System.out.println("Invalid choice. Please enter a number between 1 and 4.");
            }
        } while(choice != 4);
    }

    public void increaseSalaryByDivision() {
        Scanner scan = new Scanner(System.in);
        String division = selectDivision();
        System.out.print("Enter the percentage increase (e.g., 5 for 5%): ");
        while (!scan.hasNextDouble()) {
            System.out.print("Invalid input. Please enter a valid percentage: ");
            scan.next();
        }
        Double percentIncrease = scan.nextDouble();
        System.out.print("Enter minimum salary threshold (or 0 if none): ");
        while (!scan.hasNextDouble()) {
            System.out.print("Invalid input. Please enter a valid number: ");
            scan.next();
        }
        double minInput = scan.nextDouble();
        Double minimum = (minInput > 0) ? minInput : null;
        reportGenerator.updateDivisionSalaries(division, percentIncrease, minimum);
    }

    public void increaseSalaryByJobTitle() {
        Scanner scan = new Scanner(System.in);
        String jobTitle = selectJobTitle();
        System.out.print("Enter the percentage increase (e.g., 5 for 5%): ");
        while (!scan.hasNextDouble()) {
            System.out.print("Invalid input. Please enter a valid percentage: ");
            scan.next();
        }
        Double percentIncrease = scan.nextDouble();
        System.out.print("Enter minimum salary threshold (or 0 if none): ");
        while (!scan.hasNextDouble()) {
            System.out.print("Invalid input. Please enter a valid number: ");
            scan.next();
        }
        double minInput = scan.nextDouble();
        Double minimum = (minInput > 0) ? minInput : null;
        reportGenerator.updateJobTitleSalaries(jobTitle, percentIncrease, minimum);
    }


    public void increaseAllSalaries() {
        Scanner scan = new Scanner(System.in);
        Double percentIncrease = 0.0;
        System.out.print("Enter the percentage increase (e.g., 5 for 5%): ");
        while (!scan.hasNextDouble()) {
            System.out.print("Invalid input. Please enter a valid percentage: ");
            scan.next();
        }
        percentIncrease = scan.nextDouble();
        System.out.print("Enter minimum salary threshold (or 0 if none): ");
        while (!scan.hasNextDouble()) {
            System.out.print("Invalid input. Please enter a valid number: ");
            scan.next();
        }
        double minInput = scan.nextDouble();
        Double minimum = (minInput > 0) ? minInput : null;
        reportGenerator.updateAllSalaries(percentIncrease, minimum);
    }

    public String selectJobTitle() {
        Scanner scan = new Scanner(System.in);
        String jobTitle = "";
        int choice;
        do {
            System.out.println("Select the job title: ");
            System.out.println("1 - Sales Representative");
            System.out.println("2 - Account Executive");
            System.out.println("3 - Marketing Specialist");
            System.out.println("4 - PR Representative");
            System.out.println("5 - Software Engineer");
            System.out.println("6 - Data Analyst");
            System.out.println("7 - QA Engineer");
            System.out.println("8 - IT Support Technician");
            System.out.println("9 - System Administrator");
            System.out.println("10 - Financial Analyst");
            System.out.println("11 - Accountant");
            System.out.println("12 - Auditor");
            System.out.println("13 - HR Specialist");
            System.out.println("14 - Recruiter");
            System.out.println("15 - Manager");
            System.out.println("16 - Office Manager");
            System.out.println("17 - Legal Counsel");
            System.out.println("18 - Compliance Officer");
            System.out.println("19 - Operations Manager");
            System.out.println("20 - Customer Support Rep");
            System.out.print("Enter your choice (1-20): ");
            while (!scan.hasNextInt()) {
                System.out.print("Invalid input. Please enter a number between 1 and 20: ");
                scan.next();
            }
            choice = scan.nextInt();
            switch (choice) {
                case 1: jobTitle = "Sales Representative"; break;
                case 2: jobTitle = "Account Executive"; break;
                case 3: jobTitle = "Marketing Specialist"; break;
                case 4: jobTitle = "PR Representative"; break;
                case 5: jobTitle = "Software Engineer"; break;
                case 6: jobTitle = "Data Analyst"; break;
                case 7: jobTitle = "QA Engineer"; break;
                case 8: jobTitle = "IT Support Technician"; break;
                case 9: jobTitle = "System Administrator"; break;
                case 10: jobTitle = "Financial Analyst"; break;
                case 11: jobTitle = "Accountant"; break;
                case 12: jobTitle = "Auditor"; break;
                case 13: jobTitle = "HR Specialist"; break;
                case 14: jobTitle = "Recruiter"; break;
                case 15: jobTitle = "Manager"; break;
                case 16: jobTitle = "Office Manager"; break;
                case 17: jobTitle = "Legal Counsel"; break;
                case 18: jobTitle = "Compliance Officer"; break;
                case 19: jobTitle = "Operations Manager"; break;
                case 20: jobTitle = "Customer Support Rep"; break;
                default: System.out.println("Invalid choice. Please select a valid job title.");
            }
        } while (choice == -1);
        return jobTitle;
    }

    public String selectDivision () {
        Scanner scan = new Scanner(System.in);
        String division = "";
        int choice;
        do {
            System.out.println("Select the division:");
            System.out.println("1 - Engineering");
            System.out.println("2 - Sales");
            System.out.println("3 - Human Resources");
            System.out.println("4 - Finance");
            System.out.println("5 - Marketing");
            System.out.println("6 - Information Technology");
            System.out.println("7 - Administration");
            System.out.println("8 - Legal");
            System.out.println("9 - Operations");
            System.out.println("10 - Customer Support");
            System.out.print("Enter your choice (1-10): ");

            while (!scan.hasNextInt()) {
                System.out.print("Invalid input. Please enter a number between 1 and 10: ");
                scan.next();
            }
            choice = scan.nextInt();
            switch (choice) {
                case 1: division = "Engineering"; break;
                case 2: division = "Sales"; break;
                case 3: division = "Human Resources"; break;
                case 4: division = "Finance"; break;
                case 5: division = "Marketing"; break;
                case 6: division = "Information Technology"; break;
                case 7: division = "Administration"; break;
                case 8: division = "Legal"; break;
                case 9: division = "Operations"; break;
                case 10: division = "Customer Support"; break;
                default: System.out.println("Invalid choice. Please select a valid division.");
            }
        } while (choice == -1);
        return division;
    }
}