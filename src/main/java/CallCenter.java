import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.concurrent.ConcurrentSkipListSet;

public class CallCenter {

    private static final int CALL_PROCESSING_TIME = 3000;
    private final AutomaticTelephoneStation ats = new AutomaticTelephoneStation();
    private final ConcurrentSkipListSet<PhoneCall> processedPhoneCalls =
            new ConcurrentSkipListSet<>(Comparator.comparing(PhoneCall::getHandler)
                    .thenComparing(PhoneCall::getEndTimeCall)
                    .thenComparing(PhoneCall::getNumberPhone));

    public void receiveIncomingCall() {
        ats.generateCalls();
    }

    public void processIncomingCall() {
        try {
            while (true) {
                PhoneCall phoneCall = ats.getCallFromQueue();
                if (phoneCall != null) {
                    System.out.printf("Thread %s: I am handling the call%s.\n", Thread.currentThread().getName(), phoneCall);
                    Thread.sleep(CALL_PROCESSING_TIME);
                    phoneCall.setEndTimeCall(LocalDateTime.now());
                    phoneCall.setHandler(Thread.currentThread().getName());
                    processedPhoneCalls.add(phoneCall);
                    System.out.printf("Thread %s: %s has been handling.\n", Thread.currentThread().getName(), phoneCall);
                } else if (!ats.isGenerationCallEnable()) {
                    System.out.printf("Thread %s: the call queue is empty. I'm finishing work.\n",
                            Thread.currentThread().getName());
                    break;
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public AutomaticTelephoneStation getAts() {
        return ats;
    }

    public ConcurrentSkipListSet<PhoneCall> getProcessedPhoneCalls() {
        return processedPhoneCalls;
    }
}