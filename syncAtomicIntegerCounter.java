public class syncAtomicIntegerCounter implements Counter {
    int count = 0;

    public synchronized void incrementCounter() {
        count++;
    }

    public synchronized void setCounter(int value) {
        this.count = value;
    }

    public synchronized int getCounter() {
        return count;
    }
}
