//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            // Creating instances of Food and Drink
            Food food = new Food("F/2145786", "Nachos", 200, true);
            Drink drink = new Drink("D/2381235", "Lemonade", 100, Drink.sugarLevels.HIGH);

            // Displaying details and calculated price
            System.out.println("Food Details:");
            System.out.println(food);
            System.out.println("Calculated Price: " + food.calculatePrice() + "p");

            System.out.println("\nDrink Details:");
            System.out.println(drink);
            System.out.println("Calculated Price: " + drink.calculatePrice() + "p");
        } catch (InvalidSnackException e) {
            System.out.println("Invalid Snack: " + e.getMessage());
        }

        try {
            // Creating instances of Staff and Student
            Customer staff = new StaffCustomer("58R526", "John-Paul Clay", 400, StaffCustomer.schools.OTHER);
            Customer student = new StudentCustomer("901420", "Paige Barclay", -100);

            // Displaying details and calculated balance
            System.out.println("\nStaff Details:");
            System.out.println(staff);
            System.out.println("Calculated balance (-£2.50): " + staff.chargeAccount(250) + "p");

            System.out.println("\nStudent Details:");
            System.out.println(student);
            System.out.println("Calculated balance: " + student.chargeAccount((250)) + "p");
        } catch (InsufficientBalanceException e) {
            throw new RuntimeException(e);
        }
    }
}