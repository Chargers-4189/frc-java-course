package Exercise1;

public class Customer extends Thread {
    private final VendingMachine vendingMachine;
    public Customer(VendingMachine vendingMachine){
        this.vendingMachine = vendingMachine;
    }
    public void run(){
        System.out.println("A customer has walked to the vending machine!");
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            System.out.println("The vending machine has broken beyond repair!");
            e.printStackTrace();
        }

        System.out.println("Customer is choosing a snack...");
        int randomTimeDelay =  (int) (Math.random() * 3);
        vendingMachine.selectSnack();
        try {
            Thread.sleep(randomTimeDelay * 1000);
        } catch (InterruptedException e) {
            System.out.println("The vending machine has broken beyond repair!");
            e.printStackTrace();
        }
        System.out.println("Customer is adding coins...");
        for(int i = 0; i < 4; i++){
            try {
                Thread.sleep(randomTimeDelay * 1000);
            } catch (InterruptedException e) {
                System.out.println("The vending machine has broken beyond repair!");
                e.printStackTrace();
            }
            vendingMachine.addCoins();
        }
    }
}
