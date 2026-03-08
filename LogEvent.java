public class LogEvent {

    // I made this class to represent one log line after it gets parsed.
    // This makes the rest of the code easier to work with because
    // instead of handling raw strings everywhere, I can store each log
    // as one object with fields.

    String timestamp;
    String eventType;
    String username;
    String ipAddress;
    String rawLog;

    public LogEvent(String timestamp, String eventType, String username, String ipAddress, String rawLog) {
        this.timestamp = timestamp;
        this.eventType = eventType;
        this.username = username;
        this.ipAddress = ipAddress;
        this.rawLog = rawLog;
    }

    @Override
    public String toString() {
        return "Timestamp: " + timestamp +
               "\nEvent Type: " + eventType +
               "\nUsername: " + username +
               "\nIP Address: " + ipAddress +
               "\nRaw Log: " + rawLog;
    }
}