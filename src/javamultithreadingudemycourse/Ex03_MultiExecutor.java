package javamultithreadingudemycourse;

public class Ex03_MultiExecutor {
    import java.util.List;

    // Add any necessary member variables here
    List<Runnable> tasks;

    /*
     * @param tasks to executed concurrently
     */
    public Ex03_MultiExecutor(List<Runnable> tasks) {
        this.tasks = tasks;
    }

    /**
     * Starts and executes all the tasks concurrently
     */
    public void executeAll() {
        tasks.forEach(t -> {
            Thread thread = new Thread(t);
            thread.start();
        });// complete your code here
    }
}
