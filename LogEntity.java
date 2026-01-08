import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LogEntity {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");

    private final LocalDateTime timestamp; 
    private final String type;
    private final String message;

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getType() {
        return type;
    }

    public String getMessage() {
        return message;
    }

    public LogEntity(String type, String message) {
        this.timestamp = LocalDateTime.now(); 
        this.type = type;
        this.message = message;
    }

    @Override
    public String toString() {
        return timestamp.format(FORMATTER) + " [" + type + "] " + message;
    }
}
