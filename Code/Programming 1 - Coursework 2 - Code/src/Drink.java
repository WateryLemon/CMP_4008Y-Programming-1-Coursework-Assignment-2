class Drink extends Snack {
    public enum sugarLevels {
        HIGH,
        LOW,
        NONE
    }
    private final sugarLevels sugarLevel;

    public Drink (String snackID, String name, int basePrice, sugarLevels sugarLevel) throws InvalidSnackException {
        super(snackID, name, basePrice);
        if (snackID.charAt(0) != 'D' || !isValidSnackID(snackID)) {
            throw new InvalidSnackException("SnackID for drink does not start with 'D'");
        }
        this.sugarLevel= sugarLevel;
    }
    public sugarLevels getSugarLevel() {
        return sugarLevel;
    }

    @Override
    public int calculatePrice() {
        int priceAfterTax = basePrice;
        switch (sugarLevel) {
            case HIGH:
                priceAfterTax += 24;
                break;
            case LOW:
                priceAfterTax += 18;
                break;
            case NONE:
                break;
        }
        return priceAfterTax;
    }

    public String toString() {
        return "Drink\n" + super.toString() + "\nSugar Level: " + sugarLevel;
    }
}