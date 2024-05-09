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