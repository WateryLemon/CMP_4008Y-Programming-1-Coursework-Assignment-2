import java.util.ArrayList;
import java.util.Collections;

class SnackShop {
    protected String shopName;
    protected int turnover;
    protected ArrayList<Snack> snacks;
    protected ArrayList<Customer> customers;

    // Constructor
    public SnackShop(String shopName) {
        this.shopName = shopName;
        this.turnover = 0;
        this.snacks = new ArrayList<>();
        this.customers = new ArrayList<>();
    }

    // Accessors
    public String getShopName() { return shopName; }
    public int getTurnover() { return turnover; }

    // Mutators
    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public void addCustomer (Customer customer) {
        customers.add(customer);
    }
    public void addSnack (Snack snack) {
        snacks.add(snack);
    }

    public Customer getCustomer (String accountID) throws InvalidCustomerException {
        for (Customer customer : customers) {
            if (customer.getAccountID().equals(accountID)) {
                return customer;
            }
        } throw new InvalidCustomerException("Customer ID: " + accountID + " not found.");
    }
    public Snack getSnack (String snackID) throws InvalidSnackException {
        for (Snack snack : snacks) {
            if (snack.getSnackID().equals(snackID)) {
                return snack;
            }
        } throw new InvalidSnackException("Snack ID: " + snackID + " not found.");
    }

    public boolean processPurchase (String customerID, String snackID) throws InvalidCustomerException, InvalidSnackException, InsufficientBalanceException {
        Customer customer = getCustomer(customerID);
        Snack snack = getSnack(snackID);
        int price = customer.chargeAccount(snack.calculatePrice());

        turnover += price;
        return true; // Transaction successful
    }

    public int LargestBasePrice() {
        int largestBasePrice = Integer.MIN_VALUE;
        for (Snack snack : snacks) {
            if (snack.getBasePrice() > largestBasePrice) {
                largestBasePrice = snack.getBasePrice();
            }
        } return largestBasePrice;
    }

    public int NegativeAccountAmount() {
        int amount = 0;
        for (Customer customer : customers) {
            if (customer.getBalance() < 0) {
                amount = amount + 1;
            }
        } return amount;
    }

    public double MedianCustomerBalance() {
        ArrayList<Integer> balances = new ArrayList<>();
        for (Customer customer : customers) {
            balances.add(customer.getBalance());
        }
        System.out.println("Number of balances: " + balances.size());

        balances.sort(null);
        int size = balances.size();

        if (size % 2 == 0) {
            return (balances.get(size / 2-1) + balances.get(size / 2)) / 2.0;
        } else {
            return balances.get(size / 2);
        }
    }
}
