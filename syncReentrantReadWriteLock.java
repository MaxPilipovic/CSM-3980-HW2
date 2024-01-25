import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.Lock;

public class syncReentrantReadWriteLock implements Counter {
    int count = 0;
    private final ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();
    public void incrementCounter() {
        reentrantReadWriteLock.writeLock().lock(); //write lock (unlock)
        count++;
        reentrantReadWriteLock.writeLock().unlock();
        //write lock (lock)
    }

    public void setCounter(int value) {
        reentrantReadWriteLock.writeLock().lock();//write lock (lock)
        this.count = value;
        reentrantReadWriteLock.writeLock().unlock();
    }

    public int getCounter() {
        reentrantReadWriteLock.readLock().lock();  //read lock (lock)
        try {
            return count;
        } finally {
            reentrantReadWriteLock.readLock().unlock(); //read lock (unlock)
        }
    }
}