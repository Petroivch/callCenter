import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PhoneCall {

    private final LocalDateTime startTimeCall;
    private LocalDateTime endTimeCall;
    private final long numberPhone;
    private String handler = "missing";

    public PhoneCall(LocalDateTime startTimeCall, long numberPhone) {
        this.startTimeCall = startTimeCall;
        this.numberPhone = numberPhone;
        endTimeCall = startTimeCall;
    }

    public LocalDateTime getEndTimeCall() {
        return endTimeCall;
    }

    public void setEndTimeCall(LocalDateTime endTimeCall) {
        this.endTimeCall = endTimeCall;
    }

    public long getNumberPhone() {
        return numberPhone;
    }

    public String getHandler() {
        return handler;
    }

    public void setHandler(String handler) {
        this.handler = handler;
    }

    @Override
    public String toString() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
        return "PhoneCall{" +
                "startTimeCall=" + dtf.format(startTimeCall) +
                ", endTimeCall=" + dtf.format(endTimeCall) +
                ", numberPhone=" + numberPhone +
                ", handler='" + handler + '\'' +
                '}';
    }
}