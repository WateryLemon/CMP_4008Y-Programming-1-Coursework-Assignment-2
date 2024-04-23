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
            File file = new File("Snacks.txt");
            Scanner fileScanner = new Scanner(file);

            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] parts = line.split("@");
                String idFromText = parts[0]; // Extract snack ID from text

                if (snackID.equals(idFromText)) {
                    // Extract other details from text
                    String name = parts[1];
                    int basePrice = Integer.parseInt(parts[3]);

                    // Determine if it's a food or drink based on ID
                    boolean isHot = parts[2].equals("hot"); // For food
                    String sugarContent = parts[2]; // For drink

                    // Create Food or Drink object accordingly
                    if (idFromText.startsWith("F")) {
                        Food food = new Food(idFromText, name, basePrice, isHot);
                        System.out.println("Created Food object:\n" + food);
                    } else if (idFromText.startsWith("D")) {
                        Drink.sugarLevels content;
                        switch (sugarContent) {
                            case "high":
                                content = Drink.sugarLevels.HIGH;
                                break;
                            case "low":
                                content = Drink.sugarLevels.LOW;
                                break;
                            default:
                                content = Drink.sugarLevels.NONE;
                                break;
                        }
                        Drink drink = new Drink(idFromText, name, basePrice, content);
                        System.out.println("Created Drink object:\n" + drink);
                    }

                    // Exit loop after finding the match
                    break;
                }
            }

            fileScanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred while reading the file: " + e.getMessage());
        } catch (InvalidSnackException e) {
            throw new RuntimeException(e);
        }
    }
}