import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Simulation {
    public static SnackShop initialiseShop(String shopName, File snackFile, File customerFile) {
        SnackShop shop = new SnackShop(shopName);

        // Parse snack file and add snacks to the shop
        try (Scanner scanner = new Scanner(snackFile)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split("@");
                String snackID = parts[0];
                String name = parts[1];
                int basePrice = Integer.parseInt(parts[3]);

                try {
                    Snack snack;
                    if (snackID.startsWith("F")) {
                        boolean isHot = parts[2].equals("hot");
                        snack = new Food(snackID, name, basePrice, isHot);
                    } else if (snackID.startsWith("D")) {
                        Drink.sugarLevels sugarLevel = Drink.sugarLevels.valueOf(parts[2].toUpperCase());
                        snack = new Drink(snackID, name, basePrice, sugarLevel);
                    } else {
                        throw new InvalidSnackException("Invalid snack ID format");
                    }

                    shop.addSnack(snack);
                    System.out.println("Snack added to shop: " + name);

                } catch (InvalidSnackException e) {
                    System.err.println("Error: Invalid snack - " + e.getMessage());
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Error: Snack file not found - " + e.getMessage());
        }

        // Parse customer file and add customers to the shop
        try (Scanner scanner = new Scanner(customerFile)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split("#");
                String accountID = parts[0];
                String name = parts[1];
                int balance = Integer.parseInt(parts[2]);

                try {
                    Customer customer;
                    if (parts.length >= 4) {
                        String type = parts[3];
                        if (type.equals("STAFF")) {
                            StaffCustomer.schools school;
                            if (parts.length > 4) {
                                String schoolString = parts[4];
                                school = StaffCustomer.schools.valueOf(schoolString);
                            } else {
                                school = StaffCustomer.schools.OTHER; // Assigning OTHER if no school information is provided
                            }
                            customer = new StaffCustomer(accountID, name, balance, school);
                        } else if (type.equals("STUDENT")) {
                            customer = new StudentCustomer(accountID, name, balance);
                        } else {
                            throw new InvalidCustomerException("Customer type is missing or invalid");
                        }
                    } else {
                        customer = new Customer(accountID, name, balance);
                    }
                    shop.addCustomer(customer);

                    System.out.println("Customer added to shop: " + name);
                } catch (InvalidCustomerException e) {
                    System.err.println("Error: Invalid customer - " + e.getMessage());
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Error: Customer file not found - " + e.getMessage());
        }
        return shop;
    }

    // Implement simulateShopping method as described
    public static void simulateShopping(SnackShop shop, File transactionFile) {
        try (Scanner scanner = new Scanner(transactionFile)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                String action = parts[0].trim();
                String accountID = parts[1].trim();

                try {
                    switch (action) {
                        case "NEW_CUSTOMER":
                            String newName = parts[2].trim();
                            String type = parts[3].trim();

                            // Check if the customer ID already exists
                            boolean isDuplicate = false;
                            for (Customer existingCustomer : shop.customers) {
                                if (existingCustomer.getAccountID().equals(accountID)) {
                                    isDuplicate = true;
                                    break;
                                }
                            }

                            if (isDuplicate) {
                                System.err.println("Error: Customer ID " + accountID + " already exists.");
                                break; // Exit the switch statement
                            }

                            Customer newCustomer;

                            if (type.equals("STAFF")) {
                                StaffCustomer.schools school;
                                if (parts.length >= 6) {
                                    String schoolString = parts[4].trim();
                                    school = StaffCustomer.schools.valueOf(schoolString);

                                    int initialBalance = Integer.parseInt(parts[5].trim());

                                    newCustomer = new StaffCustomer(accountID, newName, initialBalance, school);
                                } else {
                                    school = StaffCustomer.schools.valueOf("OTHER");

                                    int initialBalance = Integer.parseInt(parts[4].trim());

                                    newCustomer = new StaffCustomer(accountID, newName, initialBalance, school);
                                }
                            } else if (type.equals("STUDENT")) {
                                int initialBalance = Integer.parseInt(parts[4].trim());

                                newCustomer = new StudentCustomer(accountID, newName, initialBalance);
                            } else {
                                int initialBalance = Integer.parseInt(parts[3].trim());

                                newCustomer = new Customer(accountID, newName, initialBalance);
                            }
                            shop.addCustomer(newCustomer);
                            System.out.println("New customer added: " + accountID);
                            break;

                        case "ADD_FUNDS":
                            int fundsToAdd = Integer.parseInt(parts[2].trim());
                            shop.getCustomer(accountID).addFunds(fundsToAdd);
                            System.out.println("Funds added to account " + accountID + ": " + fundsToAdd + "p");
                            break;

                        case "PURCHASE":
                            String snackID = parts[2].trim();
                            Snack purchasedSnack = shop.getSnack(snackID);
                            if (purchasedSnack == null) {
                                throw new InvalidSnackException("Snack ID: " + snackID + " not found.");
                            }
                            int price = purchasedSnack.calculatePrice();
                            int chargedAmount = shop.getCustomer(accountID).chargeAccount(price);
                            shop.turnover += chargedAmount;
                            System.out.println("Purchase successful for account " + accountID + ", snack " + snackID + ", price " + price + "p");
                            break;

                        default:
                            System.out.println("Invalid action: " + action);
                            break;

                    }
                } catch (InvalidCustomerException | InvalidSnackException | InsufficientBalanceException e) {
                    System.err.println("Error: " + e.getMessage());
                }
            } // After processing all transactions, print out the required statistics
            System.out.println("Largest Base Price: " + shop.LargestBasePrice() + "p");
            System.out.println("Number of Customers with Negative Balances: " + shop.NegativeAccountAmount());
            System.out.println("Median Customer Balance: " + shop.MedianCustomerBalance() + "p");
            System.out.println("Shop Turnover: " + shop.getTurnover() + "p");

        } catch (FileNotFoundException e) {
            System.err.println("Error: Transaction file not found - " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        // Define file paths
        File snackFile = new File("snacks.txt");
        File customerFile = new File("customers.txt");
        File transactionFile = new File("transactions.txt");

        // Initialise the shop
        SnackShop shop = initialiseShop("MySnackShop", snackFile, customerFile);

        // Simulate shopping
        simulateShopping(shop, transactionFile);
    }
}