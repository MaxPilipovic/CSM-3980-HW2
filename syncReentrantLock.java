import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Lock;

public class syncReentrantLock implements Counter {
    int count = 0;
    private final Lock reentrantLock = new ReentrantLock();
    public synchronized void incrementCounter() {
        //count++;
        reentrantLock.lock();
        count++;
        reentrantLock.unlock();
    }

    public void setCounter(int value) {
        reentrantLock.lock();
        this.count = value;
        reentrantLock.unlock();
    }

    public int getCounter() {
        reentrantLock.lock();
        try {
            return count;
        } finally {
            reentrantLock.unlock();
        }
    }
}
