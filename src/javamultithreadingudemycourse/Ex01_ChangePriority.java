package javamultithreadingudemycourse;

public class Ex01_ChangePriority {
    public static void main(String args[]) throws InterruptedException{
        Thread t1 = new Thread(
                () -> {
                    Thread.currentThread().setName("First course thread.");
                    System.out.println(Thread.currentThread().getName());
                }
        );

        t1.setPriority(5);
        t1.start();
        t1.join();
        System.out.println("end");
    }
}
