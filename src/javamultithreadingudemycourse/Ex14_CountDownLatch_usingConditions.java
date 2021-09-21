package javamultithreadingudemycourse;
import java.util.concurrent.locks.*;

public class Ex14_CountDownLatch_usingConditions {
	private int count;
	private java.util.concurrent.locks.ReentrantLock lock;
	private java.util.concurrent.locks.Condition condition;
    
	Ex14_CountDownLatch_usingConditions(int initialCount){
		if (initialCount < 0) {
			throw new IllegalArgumentException("Initial value must be greather than 0");
		}
		this.count = initialCount;
		this.lock = new java.util.concurrent.locks.ReentrantLock();
		this.condition = lock.newCondition();
	}

	public void await() throws InterruptedException {
		this.lock.lock();
		try {
		    
		    if (this.count > 0) {
				this.condition.await();
			}
		} finally {
		    this.lock.unlock();
		}
	}

	public void countDown() {
	    this.lock.lock();
	    try {
	        if (this.count == 0) return;
	        
	        count--;
	        if (this.count == 0) {
				this.condition.signalAll();
			}
	    } finally {
	        this.lock.unlock();
	    }

	}  

	public int getCount() {
	    this.lock.lock();
		try {
		      return this.count;
		} finally {
		    this.lock.unlock();
		}
	}
}
