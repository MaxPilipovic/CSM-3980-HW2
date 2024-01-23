import java.util.concurrent.atomic.AtomicInteger;

public class syncAtomicIntegerCounter implements Counter {
    int count = 0;
    private static final AtomicInteger atomicCounter = new AtomicInteger(0);
    public void incrementCounter() {
    atomicCounter.incrementAndGet();
    }

    public void setCounter(int value) {
        atomicCounter.set(value); //set with atomic integer value???? //does this have to be used??
    }

    public int getCounter() {
        return atomicCounter.get();
    }
}
