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

public class Customer {
    protected String accountID;
    protected String name;
    protected int balance;  // In pence
    protected String type;


    public boolean isValidAccountID(String accountID) {
        try {
            File file = new File("/Users/julest/Desktop/Files/customer.txt");
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split("@");
                String id = parts[0];
                if (id.equals(accountID)) {
                    scanner.close();
                    return true;
                }
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            System.err.println("Error: File not found - " + e.getMessage());
        }
        return false;
    }

    // Constructor account
    public Customer (String accountID, String name, String type) throws InvalidCustomerException {
        if (!isValidAccountID(accountID)) {
            throw new InvalidCustomerException("Invalid account ID.");
        }
        this.accountID = accountID;
        this.name = name;
        this.type = type;
    }

    // Constructor balance
    public Customer (String accountID, String name, int balance) throws InsufficientBalanceException {
        if ((balance < 0 && type == "STAFF") | (balance < -500 && type == "STUDENT")) {
            throw new InsufficientBalanceException("Invalid account ID or negative balance.");
        } else {
            this.accountID = accountID;
            this.name = name;
            this.balance = balance;
        }
    }

    public String getAccountID() { return accountID; }
    public String getName() { return name; }
    public int getBalance() { return balance; }
    public String getType() { return type; }

    public void addFunds(int amount) {
        if (amount > 0) {
            balance += amount;
        }
    }

    public int chargeAccount(int price) throws InsufficientBalanceException {
        return 0;
    }
}



class StudentCustomer extends Customer {
    private static final double studentDiscountPercentage = 0.05;
    private static final int maxNegativeBalance = -500;

    // Constructor
    public StudentCustomer(String accountID, String name, String type) throws InvalidCustomerException {
        super(accountID, name, type);
    }
    public StudentCustomer(String accountID, String name, int balance) throws InsufficientBalanceException {
        super(accountID, name, balance);
    }

    public static double getDiscountAmount() { return studentDiscountPercentage; }
    public static int getMaxNegativeBalance() { return maxNegativeBalance; }


    @Override
    public int chargeAccount(int price) throws InsufficientBalanceException {
        double exactCharge = price * (1 - studentDiscountPercentage);
        int chargedAmount = (int) Math.ceil(exactCharge);

        if (balance >= chargedAmount || (balance - chargedAmount) > maxNegativeBalance) {
            balance -= chargedAmount;
            return balance;
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

    public StaffCustomer(String accountID, String name, String type, schools school) throws InvalidCustomerException {
        super(accountID, name, type);
        this.school = school;
    }
    public StaffCustomer(String accountID, String name, int accountBalanceInPence, schools school) throws InsufficientBalanceException {
        super(accountID, name, accountBalanceInPence);
        this.school = school;
    }
    public schools getSchool() { return school; }

    @Override
    public int chargeAccount(int price) throws InsufficientBalanceException {
        double exactCharge = 0;
        switch (school) {
            case CMP:
                exactCharge = price * (1 - 0.10);
                break;
            case MTH:
            case BIO:
                exactCharge = price * (1 - 0.02);
                break;
            case OTHER:
                exactCharge = price * (1 - 0.00);
                break;
        }
        int chargedAmount = (int) Math.ceil(exactCharge);
        if (balance >= chargedAmount) {
            balance -= chargedAmount;
            return balance;
        } else {
            throw new InsufficientBalanceException("Insufficient balance.");
        }
    }

    @Override
    public String toString() {
        return "Staff Customer\nAccount ID: " + accountID + "\nName: " + name + "\nAccount Balance: " + balance + "p\nSchool: " + school;
    }
}
