public class Employee {
    public Integer employeeId;
    public String firstName;
    public String lastName;
    public String SSN;
    public String jobTitle;
    public String division;
    public double salary;

    public Employee(String firstName, String lastName, String SSN, String jobTitle, String division, double salary) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.SSN = SSN;
        this.jobTitle = jobTitle;
        this.division = division;
        this.salary = salary;
    }

    public Employee(int employeeId, String firstName, String lastName, String SSN, String jobTitle, String division, double salary) {
        this.employeeId = employeeId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.SSN = SSN;
        this.jobTitle = jobTitle;
        this.division = division;
        this.salary = salary;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public String getDivision() {
        return division;
    }

    public String getFirstName() {
        return firstName;
    }
    public String getLastName(){
        return lastName;
    }
    public String getJobTitle(){
        return jobTitle;
    }
    public String getSSN(){
        return SSN;
    }
    public double getSalary(){
        return salary;
    }
    public double updateSalary(double newSalary){
        return newSalary;
    }
    public String getFullName() {
        return firstName + " " + lastName;
    }

    public void setEmployeeId(Integer id) {
        this.employeeId = id;
    }

}


