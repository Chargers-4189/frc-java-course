package Exercise1;

import StudentCode.Exercise1;

public class Main {

    volatile static boolean finished = false;
    private static final VendingMachine vendingMachine = new VendingMachine();
    private static final Customer customer = new Customer(vendingMachine);
    private static final Exercise1 studentsCode = new Exercise1(vendingMachine);

    public static void main(String[] args) {
        //need threads to make this work async
        customer.start();
        studentsCode.start();
        while(finished == false){
            if(vendingMachine.row == -1){
            }
            else if(vendingMachine.dispensedSnack == vendingMachine.snacks[vendingMachine.row][vendingMachine.column]){
                finished = true;
                System.out.println("Good job! Customer is happy");
            }
            //add more test cases! make sure the machine does not error out or dispense bad snacks
        }
    }
}