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
