class InvalidSnackException extends Exception {
    public InvalidSnackException(String message) {
        super(message);
    }
}

abstract class Snack {
    protected String snackID;
    protected String name;
    protected int basePrice;    // in pence

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
    public String getSnackID() {
        return snackID;
    }
    public String getName() {
        return name;
    }
    public int getBasePrice() {
        return basePrice;
    }

    abstract int calculatePrice();

    private boolean isValidSnackID(String snackID) {
        return snackID.matches("[A-Za-z]/\\d{7}");
    }

    public String toString() {
        return "Snack ID: " + snackID + "\nName: " + name + "\nBase Price: " + basePrice + "p";
    }
}


class Food extends Snack {
    private boolean isHot;
    public static final double hotFoodSurchargePercentage = 0.10;

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
        double basePrice1 = basePrice;

        if (isHot) {
            basePrice1 += basePrice1 * hotFoodSurchargePercentage;
        }

        int price = (int) Math.ceil(basePrice1);
        return price;
    }

    public String toString() {
        return super.toString() + "\nIs Hot: " + isHot;
    }
}


class Drink extends Snack {
    public enum sugarLevel {
        HIGH,
        LOW,
        NONE
    }

    private sugarLevel sugarLevel;

    public Drink (String snackID, String name, int basePrice, sugarLevel sugarLevel) throws InvalidSnackException {
        super(snackID, name, basePrice);
        if (snackID.charAt(0) != 'D') {
            throw new InvalidSnackException("SnackID for drink does not start with 'D'");
        }
        this.sugarLevel= sugarLevel;
    }

    public sugarLevel getSugarLevel() {
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
