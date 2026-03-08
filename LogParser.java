import java.util.ArrayList;
import java.util.List;

public class LogParser {

    // This method takes all log lines and turns them into LogEvent objects.
    // If a line does not match the format I expect, I just skip it instead
    // of crashing the whole program.
    public static List<LogEvent> parseLogs(List<String> lines) {
        List<LogEvent> events = new ArrayList<>();

        for (String line : lines) {
            LogEvent event = parseSingleLog(line);

            if (event != null) {
                events.add(event);
            }
        }

        return events;
    }

    // This method parses one single log line.
    // Example format:
    // 2026-03-06 00:15:10 FAILED_LOGIN user=admin ip=192.168.1.20
    public static LogEvent parseSingleLog(String line) {
        String[] parts = line.split("\\s+");

        // Basic safety check so the code does not try to access indexes
        // that are not there.
        if (parts.length < 5) {
            return null;
        }

        // The timestamp is split into date and time, so I combine them.
        String timestamp = parts[0] + " " + parts[1];
        String eventType = parts[2];

        String username = "unknown";
        String ipAddress = "unknown";

        // I look for user= and ip= in the remaining parts.
        for (String part : parts) {
            if (part.startsWith("user=")) {
                username = part.substring(5);
            } else if (part.startsWith("ip=")) {
                ipAddress = part.substring(3);
            }
        }

        return new LogEvent(timestamp, eventType, username, ipAddress, line);
    }
}