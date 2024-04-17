//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        try {
            // Creating instances of Food and Drink
            Food hotDog = new Food("F/1234567", "Hot Dog", 350, true);
            Drink cola = new Drink("D/7654321", "Cola", 150, Drink.sugarLevel.HIGH);

            // Displaying details and calculated price
            System.out.println("Food Details:");
            System.out.println(hotDog);
            System.out.println("Calculated Price: " + hotDog.calculatePrice() + "p");

            System.out.println("\nDrink Details:");
            System.out.println(cola);
            System.out.println("Calculated Price: " + cola.calculatePrice() + "p");
        } catch (InvalidSnackException e) {
            System.out.println("Invalid Snack: " + e.getMessage());
        }
    }
}