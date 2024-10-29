package Exercise2;

import StudentCode.VendingMachine;

public class Command extends Thread{
    public boolean isfinished = false;
    private final VendingMachine vendingMachine; //public boolean isVendingMachineReady // two threads declarative, remove this to make imperative on students
    private int previousRow;
    private int previousColumn;
    private int prevCount;
    /*
     * This method runs in the background. The vending machine will run this method ONCE.
     * It is up to you to handle everything about the vending machine(coin collection and checking if the user has selected a snack)
     * This is the imperative programming approach to fix this vending machine.
     * All snacks cost 4 coins.
     */
    public Command(VendingMachine vendingMachine) {
        this.vendingMachine=vendingMachine;
        this.previousRow = vendingMachine.getSelectedRow();
        this.previousColumn = vendingMachine.getSelectedColumn();
        this.prevCount = vendingMachine.getCoinsCount();
    }
    public void StudentCode(){
        while (!isfinished){
            try {
                Thread.sleep(100);
            } catch (Exception e) {

            }
            if(vendingMachine.getCoinsCount() == 4){
                int selectedRow = vendingMachine.getSelectedRow();
                int selectedColumn = vendingMachine.getSelectedColumn();
                if(selectedRow!=previousRow && selectedColumn!=previousColumn){
                    vendingMachine.dispenseSnack(selectedRow, selectedColumn);
                    isfinished = true;
                }
            }
        }
    }
}