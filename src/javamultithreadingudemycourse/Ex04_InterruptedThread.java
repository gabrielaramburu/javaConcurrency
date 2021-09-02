package javamultithreadingudemycourse;

public class Ex04_InterruptedThread {
    public static void main(String args[]) throws InterruptedException {
        Thread t = new Thread(new InterruptedThread());
        t.start();
        System.out.println("Waiting a little...");
        Thread.sleep(2000);
        t.interrupt();
    }

    static class InterruptedThread implements Runnable {
        public void run() {
            while (true) {
                if (Thread.currentThread().isInterrupted()) {
                    System.out.println("The thread has been interrupted.");
                    break;
                }
            }
            System.out.println("Thread excecution end.");
        }
    }
}
