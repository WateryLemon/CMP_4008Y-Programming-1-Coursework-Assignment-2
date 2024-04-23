import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


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

abstract class Customer {
    protected String accountID;
    protected String name;
    protected int balance;  // In pence

    // Constructor account
    public Customer (String accountID, String name) throws InvalidCustomerException {
        if (!isValidAccountID(accountID)) {
            throw new InvalidCustomerException("Invalid account ID.");
        }
        this.accountID = accountID;
        this.name = name;
    }

    // Constructor balance
    public Customer (String accountID, String name, int balance) throws InsufficientBalanceException {
        if (balance < 0) {
            throw new InsufficientBalanceException("Invalid account ID or negative balance.");
        }
        this.accountID = accountID;
        this.name = name;
        this.balance = balance;
    }

    public String getAccountID() { return accountID; }
    public String getName() { return name; }
    public int getBalance() { return balance; }

    private boolean isValidAccountID(String snackID) {
        return snackID.matches("[A-Za-z]/\\d{7}");
    }

    public void addFunds(int amount) {
        if (amount > 0) {
            balance += amount;
        }
    }

    public abstract int chargeAccount(int snackPrice) throws InsufficientBalanceException;

}



class StudentCustomer extends Customer {
    private static final double studentDiscountPercentage = 0.05;

    // Constructor
    public StudentCustomer(String accountID, String name) throws InvalidCustomerException {
        super(accountID, name);
}
    public StudentCustomer(String accountID, String name, int balance) throws InsufficientBalanceException {
        super(accountID, name, balance);
    }

    public static double getDiscountAmount() { return studentDiscountPercentage; }

    @Override
    public int chargeAccount(int snackPrice) throws InsufficientBalanceException {
        double exactCharge = snackPrice * (1 - studentDiscountPercentage);
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
        return "Student Customer\nAccount ID: " + accountID + "\nName: " + name + "\nAccount Balance: " + balance + "p";
    }
}



class StaffCustomer extends Customer {
    public enum schools {
        CMP,
        MTH,
        BIO,
        OTHER
    }
    private schools school;

    public StaffCustomer(String accountID, String name, schools school) throws InvalidCustomerException {
        super(accountID, name);
        this.school = school;
    }
    public StaffCustomer(String accountID, String name, int accountBalanceInPence, schools school) throws InsufficientBalanceException {
        super(accountID, name, accountBalanceInPence);
        this.school = school;
    }
    public schools getSchool() { return school; }

    @Override
    public int chargeAccount(int snackPrice) throws InsufficientBalanceException {
        double exactCharge = 0;
        switch (school) {
            case CMP:
                exactCharge = snackPrice * (1 - 0.10);
                break;
            case MTH:
            case BIO:
                exactCharge = snackPrice * (1 - 0.02);
                break;
            case OTHER:
                exactCharge = snackPrice * (1 - 0.00);
                break;
        }
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
