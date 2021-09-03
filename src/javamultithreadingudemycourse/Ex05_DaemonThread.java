package javamultithreadingudemycourse;

public class Ex05_DaemonThread {
    public static void main(String[] args) {
        DeamonThread t1 = new DeamonThread();
        t1.setDaemon(true);
        t1.start();

        System.out.println("last sentence of application");
    }

    public static class DeamonThread extends Thread {
        public void run(){
            System.out.println("Waiting...");
            try {
                this.sleep(10_000);
            } catch (InterruptedException e) {
                System.err.println("error.");
            }

        }
    }
}
