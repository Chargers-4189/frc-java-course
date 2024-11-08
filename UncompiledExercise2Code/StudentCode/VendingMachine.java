package StudentCode;
/*
 * The VendingMachine class represents a broken vending machine with missing code from years of missed maintance.
 * Luckily, there is some code here that is usable. There is some methods to get the number of coins and what snack is currently selected.
 * But there is no way to know when these events happen...
 * 
 * Important Notes:
 * All snacks cost 4 coins.
 * Total number of rows: 5
 * Total number of columns: 3
 */
public class VendingMachine{

    private int coins=0;
    private int row=-1;
    private int column=-1;

    private String dispensedSnack = "chips";

    public String[][] snacks = {
        {"Chips", "Cookies", "Pretzels"},
        {"Popcorn", "Granola", "Fruit"},
        {"Candy", "Nuts", "Crackers"},
        {"Chocolate", "Biscuits", "Veggie Sticks"},
        {"Trail Mix", "Rice Cakes", "Cheese"}
    };

    public VendingMachine(){

    }

    /**
     * Returns the number of coins currently in the vending machine.
     * @return the number of coins inside the vending machine.
     */
    //Create a public function here called getCoinsCount that returns an int.
    public int getCoinsCount(){
        return 0;
    }

    /**
     * Returns the selected row inputted into the vending machine by a customer.
     * Returns -1 if no snack is chosen.
     * @return selected row from user.
     */
    //Create a public function here called getCoinsCount that returns an int.
    public int getSelectedRow(){
        return 0;
    }

    /**
     * Returns the selected column inputted into the vending machine by a customer.
     * Returns -1 if no snack is chosen.
     * @return selected column from user.
     */
    //Create a public function here called getSelectedColumn that returns an int.
    public int getSelectedColumn(){
        return 0;
    }

    /**
     * Vending machine dispense a snack at a row and column.
     * Valid input for rows: 0-4
     * Valid input for columns: 0-2
     * @param row Row that the vending machine will dispense.
     * @param column Column that the vending machine will dipsense.
     */
    
    //Create a public function here called dispenseSnack that returns nothing and takes two int parameters row and column.
    public void dispenseSnack(int row, int column){
    }

    
    //Create a public function here called getDispensedSnack that returns a String.
    public String getDispensedSnack(){
        return null;
    }

    //Create a public function here called runVendingMachine that returns nothing.
    public void runVendingMachine(){

    }

    //Create a public function here called addCoins that returns nothing.
    public void addCoins(){
    }

    //Create a public function here called getCoinsCount that returns nothing.
    public void selectSnack(){
    }
    
}

