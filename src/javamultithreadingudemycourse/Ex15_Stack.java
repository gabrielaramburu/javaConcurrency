package javamultithreadingudemycourse;
import java.util.*;
import java.util.concurrent.*;

public class Ex15_Stack {
    public static void main(String[] args) throws InterruptedException {
        StandarStack<Integer> stack = new StandarStack<Integer>();
        ThreadLocalRandom random = ThreadLocalRandom.current();

        Thread t1 = new Thread(()->{
            while(true) {
                stack.push(random.nextInt());
            }
        });

        Thread t2 = new Thread(()->{
           while(true) {
                stack.pop();
            }
        });
        t1.setDaemon(true);
        t2.setDaemon(true);
        t1.start();
        t2.start();

        Thread.sleep(10000);
        System.out.format("%,d number of operations \n", stack.getCounter());
    }
}

class StandarStack<T>  {
    private Node<T> head;
    private int counter;

    public synchronized void push(T value) {

        Node<T> newNode = new Node<T>(value);
        newNode.next = head;
        head = newNode;
        counter++;
    }

    public synchronized T pop() {
        if (head != null) {
            T value = head.value;
            head = head.next;
            counter++;
            return value;
        }
        return null;
    }

    public int getCounter(){
        return counter;
    }
}
class Node <T> {
    T value;
    Node next;

    Node (T value){
        this.value = value;
        this.next = null;
    }
}