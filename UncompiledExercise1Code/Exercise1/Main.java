package Exercise1;

import StudentCode.Exercise1;

public class Main {

    volatile static boolean finished = false;

    public static void main(String[] args) {

        VendingMachine machine = new VendingMachine();
        Exercise1 studentsCode = new Exercise1();

        //need threads to make this work async
        Thread dispenseCheckerThread = new Thread(()->{
            studentsCode.startVendingMachine(machine);
        });
        dispenseCheckerThread.start();

        Thread customerThread = new Thread(()->{
            System.out.println("A customer has walked to the vending machine!");
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                System.out.println("The vending machine has broken beyond repair!");
                e.printStackTrace();
            }

            System.out.println("Customer is choosing a snack...");
            int randomTimeDelay =  (int) (Math.random() * 3);
            try {
                Thread.sleep(randomTimeDelay * 1000);
            } catch (InterruptedException e) {
                System.out.println("The vending machine has broken beyond repair!");
                e.printStackTrace();
            }
            machine.selectSnack();
        });
        
        customerThread.start();
        Thread assignmentValidatorThread = new Thread(()->{
            while(finished == false){
                if(machine.row == -1){
                    System.out.println(machine.row);
                }
                else if(machine.dispensedSnack == machine.snacks[machine.row][machine.column]){
                    finished = true;
                    System.out.println("Good job! Customer is happy");
                }
            }
        });
        assignmentValidatorThread.start();
    }
}