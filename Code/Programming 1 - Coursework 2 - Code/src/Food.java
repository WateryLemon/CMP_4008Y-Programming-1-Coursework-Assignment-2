class Food extends Snack {
    private final boolean isHot;
    private static final double hotFoodSurchargePercentage = 0.10;

    // Constructor
    public Food (String snackID, String name, int basePrice, boolean isHot) throws InvalidSnackException {
        super(snackID, name, basePrice);
        if (snackID.charAt(0) != 'F' || !isValidSnackID(snackID)) {
            throw new InvalidSnackException("SnackID for food does not start with 'F'");
        }
        this.isHot = isHot;
    }
    public boolean getIsHot() {
        return isHot;
    }

    @Override
    public int calculatePrice() {
        double unroundedBasePrice = basePrice;
        if (isHot) {
            unroundedBasePrice += unroundedBasePrice * hotFoodSurchargePercentage;
        }
        return (int) Math.ceil(unroundedBasePrice);
    }
    public String toString() {
        return "Food\n" + super.toString() + "\nIs Hot: " + isHot;
    }
}