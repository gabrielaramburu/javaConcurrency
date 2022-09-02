package javamultithreadingudemycourse;

public class Ex05_DaemonThread {
    public static void main(String[] args) {
        DeamonThread t1 = new DeamonThread();
        //when the thread is NOT setted as deamon, the application keeps alive and show the message
        //"End thread..." on console
        //However when the thread is set as Deamon, "End thread..."
        //is never showen because the JVM was ended (therefore the deamon thread also is dead)
        
        //The idea of deamon thread is to provide services to users thread. Therefore is not make sense to keep
        //deamon thread running if we don't have users thread running.
        
        t1.setDaemon(false); 
        t1.start();

        System.out.println("last sentence of application");
        //this main thread does not die until the last not deamon thread finishes
   
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
