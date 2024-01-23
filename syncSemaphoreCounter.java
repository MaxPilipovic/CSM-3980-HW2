import java.util.concurrent.Semaphore;

public class syncSemaphoreCounter implements Counter {
    private static final Semaphore mutex = new Semaphore(1);
    int count = 0;

    public synchronized void incrementCounter() {
        mutex.acquireUninterruptibly();
        count++;
        mutex.release();
    }

    public synchronized void setCounter(int value) {
        mutex.acquireUninterruptibly();
        this.count = value;
        mutex.release();
    }

    public synchronized int getCounter() {
        mutex.acquireUninterruptibly();
        try {
            return count;
        } finally {
            mutex.release();
        }
    }
}