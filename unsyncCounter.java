public class unsyncCounter implements Counter {
    int count = 0;

    public void incrementCounter() {
        count++;
    }

    public void setCounter(int value) {
        this.count = value;
    }

    public int getCounter() {
        return count;
    }
}