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

    public boolean isValidSnackID(String snackID) {
        try (Scanner scanner = new Scanner(new File("snacks.txt"))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split("@");
                String id = parts[0];
                if (id.equals(snackID)) {
                    return true;
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Error: File not found - " + e.getMessage());
        }
        return false;
    }

    abstract int calculatePrice();

    public String toString() {
        return "Snack ID: " + snackID + "\nName: " + name + "\nBase Price: " + basePrice + "p";
    }
}