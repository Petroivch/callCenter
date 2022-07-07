public class Main {

    public static final int NUMBER_OF_TREADS = 31;
    public static final int DELAY_TIME_BEFORE_SHUTDOWN_ATS = 5000;

    public static void main(String[] args) {
        ThreadGroup handlerGroup = new ThreadGroup("Group of specialists");
        final CallCenter callCenter = new CallCenter();
        for (int i = 0; i < NUMBER_OF_TREADS; i++) {
            if (i == 0) {
                new Thread(null, callCenter::receiveIncomingCall, "ATC").start();
            } else {
                new Thread(handlerGroup, callCenter::processIncomingCall, i + " specialist").start();
            }
        }
        try {
            Thread.sleep(DELAY_TIME_BEFORE_SHUTDOWN_ATS);
            System.out.printf("Thread %s: I am turning off the ATC.\n", Thread.currentThread().getName());
            callCenter.getAts().disableGenerationCall();
            Thread[] handlerThreads = new Thread[NUMBER_OF_TREADS - 1];
            handlerGroup.enumerate(handlerThreads);
            for (Thread thread : handlerThreads) {
                thread.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.printf("There are generated ATC calls: %d\n", callCenter.getAts().getNumberOfCallsForAllTime());
        System.out.printf("There are handled calls: %d\n", callCenter.getProcessedPhoneCalls().size());
        System.out.println("Handled calls:");
        callCenter.getProcessedPhoneCalls().parallelStream()
                .forEach(System.out::println);
    }
}