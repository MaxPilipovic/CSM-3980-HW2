import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class mainCounter {
    private static final int minThread = 1;
    private static final int maxThread = 12; //1 to 2c. where c is number of cores //6 cores on my pc
    private static final int minSize = 1;
    private static final int maxSize = 999999;
    private static final int counterMinimum = 1;
    private static final int counterMaximum = 6;

    //Decided to do no error handling

    public static void main(String[] args ) {
        System.out.println("Cores: " + Runtime.getRuntime().availableProcessors());
        System.out.println("");

        Scanner scanner = new Scanner(System.in);
        System.out.println("Select type of Counter to test. (Options are 1. Unsyncronized Counter, 2. Synchronized Counter, 3. Semaphore Counter, 4.Reentrant Lock" +
                " 5. ReentrantReadWriteLock 6. Atomic Integer Counter");
        int counterNumber = scanner.nextInt();

        System.out.println("Enter number of threads");
        int threadNumber = scanner.nextInt();

        System.out.println("Enter size of counter");
        int counterSize = scanner.nextInt();

        Counter counter = selectCounter(counterNumber);
        testCounter(counter, threadNumber, counterSize);
    }

    private static void testCounter(Counter counter, int threadNumber, int counterSize) {
        Stopwatch stopwatch = new Stopwatch();
        List<myThread> threads = new LinkedList<>();
        for (int i = 0; i < threadNumber; i++) {
            myThread thread = new myThread(counter, counterSize);
            thread.start();
            threads.add(thread);
        }

        for (int i = 0; i < threads.size(); i++) { //Loop to wait for each thread to finish and begin next
            try {
                threads.get(i).join();
            } catch (InterruptedException ex) {
                System.out.println("Something is not working");
            }
        }

        double timeElapsed = stopwatch.elapsedTime();
        System.out.println("Time: " + timeElapsed);
        System.out.println("Value: " + counter.getCounter());

    }

    //Mythread class for thread creation
    private static class myThread extends Thread {
        private final Counter counter; //Counter type
        private final int size; //Size

        //Constructor
        public myThread(Counter counter, int operations) {
            this.counter = counter;
            this.size = operations;
        }

        //Run method, generates random number between 0 and 2 and tests each case
        @Override
        public void run() {
            Random random = new Random();
            for (int i = 0; i < size; i++) {
                int number = random.nextInt(3);
                if (number == 0) {
                    counter.incrementCounter();
                }
                else if (number == 1) {
                    counter.setCounter(10);
                    System.out.println("Setting to 10");
                }
                else if (number == 2) {
                    counter.getCounter();
                }
            }
        }

    }

    //Select method
    private static Counter selectCounter(int counterSelect) {
        if (counterSelect == 1) {
            System.out.println("Selected unsyncCounter");
            return new unsyncCounter();
        }
        else if (counterSelect == 2) {
            System.out.println("Selected syncCounter");
            return new syncCounter();
        }
        /*
        else if (counterSelect == 3) {
            System.out.println("Selected syncSemaphore");
            return new syncCounter();
        }
        else if (counterSelect == 4) {
            System.out.println("Selected syncReentrantLock");
            return new syncCounter();
        }
        else if (counterSelect == 5) {
            System.out.println("Selected syncReentrantReadWriteLock");
            return new syncCounter();
        }
        else if (counterSelect == 6) {
            System.out.println("Selected AtomicInteger");
            return new syncCounter();
        }
        */
        else {
            System.out.println("That selection does not exist");
            return null;
        }
    }
}
