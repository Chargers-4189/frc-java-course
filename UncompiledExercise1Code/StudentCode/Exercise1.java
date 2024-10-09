package StudentCode;

import Exercise1.VendingMachine;

public class Exercise1{
    VendingMachine vendingMachine; //public boolean isVendingMachineReady // two threads declarative, remove this to make imperative on students
    
    /*
     * This method runs in the background. The vending machine will run this method ONCE.
     * It is up to you to handle everything about the vending machine(coin collection and checking if the user has selected a snack)
     * This is the imperative programming approach to fix this vending machine.
     * All snacks cost 4 coins.
     */
    public void startVendingMachine(VendingMachine vendingMachine){
        this.vendingMachine = vendingMachine;
    }
}