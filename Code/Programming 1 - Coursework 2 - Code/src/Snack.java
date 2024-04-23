class InvalidSnackException extends Exception {
    public InvalidSnackException(String message) {
        super(message);
    }
}

abstract class Snack {
    protected String snackID;
    protected String name;
    protected int basePrice;    // In pence

    // Constructor
    public Snack (String snackID, String name, int basePrice) throws InvalidSnackException {
        if (!isValidSnackID(snackID) || basePrice < 0) {
            throw new InvalidSnackException("Snack ID is invalid or the base price is bellow 0");
        }
        this.snackID = snackID;
        this.name = name;
        this.basePrice = basePrice;
    }

    // Accessor Methods
    public String getSnackID() { return snackID; }
    public String getName() { return name; }
    public int getBasePrice() { return basePrice; }

    private boolean isValidSnackID(String snackID) {
        return snackID.matches("[A-Za-z]/\\d{7}");
    }

    abstract int calculatePrice();

    public String toString() {
        return "Snack ID: " + snackID + "\nName: " + name + "\nBase Price: " + basePrice + "p";
    }
}


class Food extends Snack {
    private boolean isHot;
    private static final double hotFoodSurchargePercentage = 0.10;

    // Constructor
    public Food (String snackID, String name, int basePrice, boolean isHot) throws InvalidSnackException {
        super(snackID, name, basePrice);
        if (snackID.charAt(0) != 'F') {
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
        int price = (int) Math.ceil(unroundedBasePrice);
        return price;
    }
    public String toString() {
        return super.toString() + "\nIs Hot: " + isHot;
    }
}


class Drink extends Snack {
    public enum sugarLevels {
        HIGH,
        LOW,
        NONE
    }
    private sugarLevels sugarLevel;

    public Drink (String snackID, String name, int basePrice, sugarLevels sugarLevel) throws InvalidSnackException {
        super(snackID, name, basePrice);
        if (snackID.charAt(0) != 'D') {
            throw new InvalidSnackException("SnackID for drink does not start with 'D'");
        }
        this.sugarLevel= sugarLevel;
    }
    public sugarLevels getSugarLevel() {
        return sugarLevel;
    }

    @Override
    public int calculatePrice() {
        int price = basePrice;
        switch (sugarLevel) {
            case HIGH:
                price += 24;
                break;
            case LOW:
                price += 18;
                break;
            case NONE:
                price += 0;
                break;
        }
        return price;
    }

    public String toString() {
        return super.toString() + "\nSugar Level: " + sugarLevel;
    }
}
