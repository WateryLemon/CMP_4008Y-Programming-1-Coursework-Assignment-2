import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

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

    boolean isValidSnackID(String snackString) {
        String[] parts = snackString.split("@");
        if (parts.length < 1)  { return false; }

        String snackID = parts[0];
        if (snackID.length() != 9) { return false; }

        char type = snackID.charAt(0);
        if (type != 'F' && type != 'D' && snackID.charAt(1) != '/') {return false; }

        for (int i = 2; i < snackID.length(); i++) {
            if (!Character.isDigit(snackID.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    abstract int calculatePrice();

    public String toString() {
        return "Snack ID: " + snackID + "\nName: " + name + "\nBase Price: " + basePrice + "p";
    }
}