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


