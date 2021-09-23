package javamultithreadingudemycourse;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.util.concurrent.locks.*;

public class Ex15_Stack {
    public static void main(String[] args) throws InterruptedException {
        StandarStack<Integer> stack = new StandarStack<Integer>();
        //LockFreeStack<Integer> stack = new LockFreeStack<Integer>();
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

class LockFreeStack<T> {
    private AtomicReference<Node<T>> head = new AtomicReference<Node<T>>();
    private AtomicInteger counter = new AtomicInteger(0);

    public void push(T value) {
        Node<T> newNode = new Node(value);
        while(true) {
            Node<T> currentHead = head.get();
            newNode.next = currentHead;
            if (head.compareAndSet(currentHead, newNode)) {
                counter.incrementAndGet();        
                break;
            } else {
                LockSupport.parkNanos(1);
            }
        }
        //counter.incrementAndGet();

    }

    public T pop() {
        Node<T> currentHeadNode = head.get();
        Node<T> newHeadNode;

        while (currentHeadNode != null)  {
            newHeadNode = head.get().next;
            if (head.compareAndSet(currentHeadNode, newHeadNode)) {
               break;
            } else {
                LockSupport.parkNanos(1);
                currentHeadNode = head.get();
            }
        }
        counter.incrementAndGet();
        return currentHeadNode != null ? currentHeadNode.value : null;
    }

    public Integer getCounter() {

        return counter.get();
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