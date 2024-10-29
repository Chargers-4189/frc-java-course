package Exercise2;

import StudentCode.VendingMachine;

public class Main {
    private static final VendingMachine vendingMachine = new VendingMachine();
    private static final Customer customer = new Customer(vendingMachine);
    private static final Command studentsCode = new Command(vendingMachine);

    public static void main(String[] args) {
        //need threads to make this work async
        customer.start();
        studentsCode.StudentCode();
        if(vendingMachine.getDispensedSnack().equals(vendingMachine.snacks[vendingMachine.getSelectedRow()][vendingMachine.getSelectedColumn()])){
            System.out.println("Good job! Customer is happy");
        }else{
            System.out.println("BadJob, customer broke vending machine.");
        }
    }
}