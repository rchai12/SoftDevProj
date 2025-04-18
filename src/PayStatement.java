public class PayStatement {
    private int paymentId;
    private int employeeId;
    private String paymentDate;
    private double amount;

    // Constructor to initialize PayStatement fields
    public PayStatement(int paymentId, int employeeId, String paymentDate, double amount) {
        this.paymentId = paymentId;
        this.employeeId = employeeId;
        this.paymentDate = paymentDate;
        this.amount = amount;
    }

    // Method to print a formatted version of the pay statement
    public void printPayStatement() {
        System.out.println("========== PAY STATEMENT ==========");
        System.out.println("Payment ID   : " + paymentId);
        System.out.println("Employee ID  : " + employeeId);
        System.out.println("Payment Date : " + paymentDate);
        System.out.println("Amount Paid  : $" + String.format("%.2f", amount));
        System.out.println("===================================");
    }

    // Optional: Getters in case you want to access this data elsewhere
    public int getPaymentId() {
        return paymentId;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public double getAmount() {
        return amount;
    }
}

/* 

 * How to Call:
PayStatement statement = new PayStatement(1001, 205, "2025-04-15", 4500.00);
statement.printPayStatement();


 * Example Output:
========== PAY STATEMENT ==========
Payment ID   : 1001
Employee ID  : 205
Payment Date : 2025-04-15
Amount Paid  : $4500.00
===================================
 */