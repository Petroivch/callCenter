
import java.time.LocalDateTime;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;

public class AutomaticTelephoneStation {

    private static final int CALLS_RECEIPT_INTERVAL = 1000;
    private static final int NUMBER_OF_CALLS = 60;
    private static final long minPhoneNumber = 8100000001L;
    private static final long maxPhoneNumber = 8999999999L;
    private static final ConcurrentLinkedQueue<PhoneCall> queueOfPhoneCalls = new ConcurrentLinkedQueue<>();
    private volatile boolean generation;
    private volatile int numberOfCallsForAllTime = 0;

    public AutomaticTelephoneStation() {}

    public PhoneCall getCallFromQueue() {
        return queueOfPhoneCalls.poll();
    }

    public void addCallInQueue() {
        queueOfPhoneCalls.offer(createPhoneCall());
    }

    public boolean isEmptyCallInQueue() {
        return queueOfPhoneCalls.isEmpty();
    }

    public void generateCalls() {
        try {
            enableGenerationCall();
            while (generation) {
                for (int i = 0; i < NUMBER_OF_CALLS; i++) {
                    addCallInQueue();
                }
                System.out.printf("Thread %s: generated %d incoming calls.\n", Thread.currentThread().getName(),
                        NUMBER_OF_CALLS);
                numberOfCallsForAllTime += NUMBER_OF_CALLS;
                Thread.sleep(CALLS_RECEIPT_INTERVAL);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.printf("Thread %s: finished.\n", Thread.currentThread().getName());
    }

    public void enableGenerationCall() {
        generation = true;
    }

    public void disableGenerationCall() {
        generation = false;
    }

    public boolean isGenerationCallEnable() {
        return generation;
    }

    public int getNumberOfCallsForAllTime() {
        return numberOfCallsForAllTime;
    }

    public PhoneCall createPhoneCall() {
        return new PhoneCall(LocalDateTime.now(), getPhoneNumberRandom());
    }

    public long getPhoneNumberRandom() {
        return new Random().nextLong((maxPhoneNumber - minPhoneNumber) + 1) + minPhoneNumber;
    }
}