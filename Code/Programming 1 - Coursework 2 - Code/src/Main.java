//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Creating instances of Food and Drink
        Scanner myFood = new Scanner(System.in);
        System.out.println("Enter Snack ID");
        String snackID = myFood.nextLine();  // Read user input

        try {
            File file = new File("/Users/julest/Desktop/Files/snacks.txt");
            Scanner fileScanner = new Scanner(file);

            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] parts = line.split("@");
                String newID = parts[0]; // Extract snack ID from text

                if (snackID.equals(newID)) {
                    // Extract other details from text
                    String newName = parts[1];
                    int newBasePrice = Integer.parseInt(parts[3]);

                    // Determine if it's a food or drink based on ID
                    boolean isHot = parts[2].equals("hot"); // For food
                    String sugarLevels = parts[2]; // For drink

                    // Create Food or Drink object accordingly
                    if (newID.startsWith("F")) {
                        Food food = new Food(newID, newName, newBasePrice, isHot);

                        System.out.println("Food Details:");
                        System.out.println(food);
                        System.out.println("Calculated Price: " + food.calculatePrice() + "p");
                    } else if (newID.startsWith("D")) {
                        Drink.sugarLevels newSugarLevel = switch (sugarLevels) {
                            case "high" -> Drink.sugarLevels.HIGH;
                            case "low" -> Drink.sugarLevels.LOW;
                            default -> Drink.sugarLevels.NONE;
                        };
                        Drink drink = new Drink(newID, newName, newBasePrice, newSugarLevel);

                        System.out.println("\nDrink Details:");
                        System.out.println(drink);
                        System.out.println("Calculated Price: " + drink.calculatePrice() + "p");
                    }

                }
            }

            fileScanner.close();
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (InvalidSnackException e) {
            throw new RuntimeException(e);
        }

        try {
            File file = new File("/Users/julest/Desktop/Files/customers.txt");
            Scanner fileScanner = new Scanner(file);

            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] parts = line.split("@");
                String newID = parts[0]; // Extract snack ID from text

                if (snackID.equals(newID)) {
                    // Extract other details from text
                    String newName = parts[1];
                    int newBasePrice = Integer.parseInt(parts[3]);

                    // Determine if it's a food or drink based on ID
                    boolean isHot = parts[2].equals("hot"); // For food
                    String sugarLevels = parts[2]; // For drink
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}