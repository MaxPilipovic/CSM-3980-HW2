import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class mainCounter {
    private static final int Min= 1;
    private static final int Max = 12; //1 to 2c. where c is number of cores //6 cores on my pc


    //Decided to do no error handling

    public static void main(String[] args ) {
        System.out.println("Cores: " + Runtime.getRuntime().availableProcessors());
        System.out.println("");

        Scanner scanner = new Scanner(System.in);
        System.out.println("Select type of Counter to test. (Options are 1. Unsyncronized Counter, 2. Synchronized Counter, 3. Semaphore Counter, 4.Reentrant Lock" +
                " 5. ReentrantReadWriteLock 6. Atomic Integer Counter");
        int counterNumber = scanner.nextInt();

        //System.out.println("Enter number of threads");
        System.out.println("Using: " + Runtime.getRuntime().availableProcessors() + " Cores");
        int threadNumber = Runtime.getRuntime().availableProcessors(); //optimal amount of threads in system

        //System.out.println("Enter size of counter");
        int counterSize = 1000000; //1 Mil for big counter size
        System.out.println("Using counter size 1,000,000");

        System.out.println("Enter method test type. (Options are 1. Increment 2. Set 3. Get 4. All");
        int testType = scanner.nextInt();

        Counter counter = selectCounter(counterNumber);
        testCounter(counter, threadNumber, counterSize, testType);
    }

    private static void testCounter(Counter counter, int threadNumber, int counterSize, int testType) {
        Stopwatch stopwatch = new Stopwatch();
        List<myThread> threads = new LinkedList<>();
        for (int i = 0; i < threadNumber; i++) {
            myThread thread = new myThread(counter, counterSize, testType);
            thread.start();
            threads.add(thread);
        }

        //Join threads
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
        private final int testType; //Type of test

        //Constructor
        public myThread(Counter counter, int size, int testType) {
            this.counter = counter;
            this.size = size;
            this.testType = testType;
        }

        //Run method, generates random number between 0 and 2 and tests each case
        @Override
        public void run() {
            Random random = new Random();
            for (int i = 0; i < size; i++) {
                //int number = random.nextInt(3);

                if (testType == 1) {
                    counter.incrementCounter();
                }
                else if (testType == 2) {
                    counter.setCounter(10);
                    //System.out.println("Setting to 10");
                }
                else if (testType == 3) {
                    counter.getCounter();
                }
                else if(testType == 4) {
                    int randomvalue = random.nextInt(3) + 1;
                    switch (randomvalue) {
                        case 1:
                            counter.incrementCounter();
                            break;

                        case 2:
                            counter.setCounter(10);
                            break;
                        case 3:
                            counter.getCounter();
                            break;
                    }
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
        else if (counterSelect == 3) {
            System.out.println("Selected syncSemaphore");
            return new syncSemaphoreCounter();
        }
        else if (counterSelect == 4) {
            System.out.println("Selected syncReentrantLock");
            return new syncReentrantLock();
        }
        else if (counterSelect == 5) {
            System.out.println("Selected syncReentrantReadWriteLock");
            return new syncReentrantReadWriteLock();
        }
        else if (counterSelect == 6) {
            System.out.println("Selected AtomicInteger");
            return new syncAtomicIntegerCounter();
        }
        else {
            System.out.println("That selection does not exist");
            return null;
        }
    }
}
