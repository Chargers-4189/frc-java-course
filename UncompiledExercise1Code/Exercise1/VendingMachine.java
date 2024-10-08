package Exercise1;
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

    int coins;
    int row;
    int column;

    String dispensedSnack;

    String[][] snacks = {
        {"Chips", "Cookies", "Pretzels"},
        {"Popcorn", "Granola", "Fruit"},
        {"Candy", "Nuts", "Crackers"},
        {"Chocolate", "Biscuits", "Veggie Sticks"},
        {"Trail Mix", "Rice Cakes", "Cheese"}
    };

    VendingMachine(){
        coins = 0;
        row = -1;
        column = -1;
    }

    /**
     * Returns the number of coins currently in the vending machine.
     * @return the number of coins inside the vending machine.
     */
    public int getCoinsCount(){
        return coins;
    }

    /**
     * Returns the selected row inputted into the vending machine by a customer.
     * Returns -1 if no snack is chosen.
     * @return selected row from user.
     */
    public int getSelectedRow(){
        return row;
    }

    /**
     * Returns the selected column inputted into the vending machine by a customer.
     * Returns -1 if no snack is chosen.
     * @return selected column from user.
     */
    public int getSelectedColumn(){
        return column;
    }

    /**
     * Vending machine dispense a snack at a row and column.
     * Valid input for rows: 0-4
     * Valid input for columns: 0-2
     * @param row Row that the vending machine will dispense.
     * @param column Column that the vending machine will dipsense.
     */
    public void dispenseSnack(int row, int column){
        System.out.println("Dispending... " + snacks[row][column] + "!");
        dispensedSnack = snacks[row][column];
    }

    /*
    * Helper functions that students do not need access to. 
    */
    protected void runVendingMachine(){

    }

    protected void addCoins(){
        System.out.println("Customer has added a coin...");
        coins = coins + 1;
    }

    protected void selectSnack(){
        row = (int) (Math.random() * 5);
        column = (int) (Math.random() * 3);
        System.out.println("Customer has chosen a snack!");
    }
    
}

