package javamultithreadingudemycourse;

public class Ex05_DaemonThread {
    public static void main(String[] args) {
        DeamonThread t1 = new DeamonThread();
        //when this line is commented the application keeps alive and show the message
        //"End thread..." on console
        //However when the threas is stting as Deamon, the last sentence on thred
        //never is showen
        
        t1.setDaemon(true); 
        t1.start();

        System.out.println("last sentence of application");
    }

    public static class DeamonThread extends Thread {
        public void run(){
            System.out.println("Waiting...");
            try {
                this.sleep(5_000);
            } catch (InterruptedException e) {
                System.err.println("error.");
            }
            System.out.println("End thread");
        }
    }
}
