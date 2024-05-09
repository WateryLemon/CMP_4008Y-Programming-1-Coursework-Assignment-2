import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

class InvalidCustomerException extends Exception {
    public InvalidCustomerException(String message) {
        super(message);
    }
}
class InsufficientBalanceException extends Exception {
    public InsufficientBalanceException(String message) {
        super(message);
    }
}

class Customer {
    protected String accountID;
    protected String name;
    protected int balance;  // In pence

    // Constructor account
    public Customer (String accountID, String name) throws InvalidCustomerException {
        if (!isValidAccountID(accountID)) {
            throw new InvalidCustomerException("Invalid Account ID");
        }
        this.accountID = accountID;
        this.name = name;
        this.balance = 0;
    }

    public Customer (String accountID, String name, int balance) throws InvalidCustomerException {
        if (!isValidAccountID(accountID) || balance < 0) {
            throw new InvalidCustomerException("Invalid Account ID or negative balance");
        }
        this.accountID = accountID;
        this.name = name;
        this.balance = balance;
    }

    public String getAccountID() { return accountID; }
    public String getName() { return name; }
    public int getBalance() { return balance; }

    public boolean isValidAccountID(String accountID) {
        return accountID != null && accountID.matches("[A-Za-z0-9]{6,7}");
    }

    public void addFunds(int amount) {
        if (amount > 0) {
            balance += amount;
        }
    }

    public int chargeAccount(int price) throws InsufficientBalanceException {
        if (balance >= price) {
            balance -= price;
            return price;
        } else {
            throw new InsufficientBalanceException("Insufficient Balance");
        }
    }
}



class StudentCustomer extends Customer {
    private static final double studentDiscountPercentage = 0.05;
    private static final int maxNegativeBalance = -500;

    // Constructor
    public StudentCustomer(String accountID, String name) throws InvalidCustomerException {
        super(accountID, name);
    }

    public StudentCustomer (String accountID, String name, int balance) throws InvalidCustomerException {
        super(accountID, name, balance);
    }

    public static double getDiscountAmount() { return studentDiscountPercentage; }

    @Override
    public int chargeAccount(int price) throws InsufficientBalanceException {
        double exactCharge = price * (1 - studentDiscountPercentage);
        int chargedAmount = (int) Math.ceil(exactCharge);

        if ((balance - chargedAmount) >= maxNegativeBalance) {
            balance -= chargedAmount;
            return chargedAmount;
        } else {
            throw new InsufficientBalanceException("Insufficient balance.");
        }
    }

    @Override
    public String toString() {
        return "Student Customer\nAccount ID: " + accountID + "\nName: " + name + "\nAccount Balance: " + balance;
    }
}



class StaffCustomer extends Customer {
    enum schools { CMP, MTH, BIO, OTHER }
    private final schools school;

    public StaffCustomer(String accountID, String name, schools school) throws InvalidCustomerException {
        super(accountID, name);
        this.school = school;
    }
    public StaffCustomer(String accountID, String name, int balance, schools school) throws InvalidCustomerException {
        super(accountID, name, balance);
        this.school = school;
    }

    @Override
    public int chargeAccount(int price) throws InsufficientBalanceException {
        double exactCharge = switch (school) {
            case CMP -> price * (1 - 0.10);
            case MTH, BIO -> price * (1 - 0.02);
            case OTHER -> price * (1 - 0.00);
        };
        int chargedAmount = (int) Math.ceil(exactCharge);
        if (balance >= chargedAmount) {
            balance -= chargedAmount;
            return chargedAmount;
        } else {
            throw new InsufficientBalanceException("Insufficient balance.");
        }
    }

    @Override
    public String toString() {
        return "Staff Customer\nAccount ID: " + accountID + "\nName: " + name + "\nAccount Balance: " + balance + "p\nSchool: " + school;
    }
}