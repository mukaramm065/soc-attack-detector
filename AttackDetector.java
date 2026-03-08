import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AttackDetector {

    // This method analyzes the parsed events and prints a summary.
    // For version 1, I am mainly looking for:
    // 1. repeated failed logins from the same IP
    // 2. a successful login after those failures
    public static void analyzeLogs(List<LogEvent> events) {

        // This map keeps track of how many failed logins each IP had.
        Map<String, Integer> failedLoginCounts = new HashMap<>();

        // This map stores which username was being targeted by each IP.
        // I kept this simple and only store the last one seen for that IP.
        Map<String, String> targetedUsers = new HashMap<>();

        // This map tells me if a success login happened from the same IP.
        Map<String, Boolean> successAfterFailure = new HashMap<>();

        for (LogEvent event : events) {

            // If the event is a failed login, I increase the count for that IP.
            if (event.eventType.equals("FAILED_LOGIN")) {
                int currentCount = failedLoginCounts.getOrDefault(event.ipAddress, 0);
                failedLoginCounts.put(event.ipAddress, currentCount + 1);

                targetedUsers.put(event.ipAddress, event.username);
            }

            // If the event is a success login, I only care if this IP already
            // had failed login attempts before.
            if (event.eventType.equals("SUCCESS_LOGIN")) {
                if (failedLoginCounts.containsKey(event.ipAddress)) {
                    successAfterFailure.put(event.ipAddress, true);
                    targetedUsers.put(event.ipAddress, event.username);
                }
            }
        }

        // Print the final investigation summary.
        System.out.println("SOC Investigation Report");
        System.out.println("------------------------");

        boolean foundSuspiciousActivity = false;

        for (String ip : failedLoginCounts.keySet()) {
            int failCount = failedLoginCounts.get(ip);
            String username = targetedUsers.get(ip);
            boolean hadSuccess = successAfterFailure.getOrDefault(ip, false);

            // I decided that 3 or more failed logins is suspicious enough
            // for this project 
            if (failCount >= 3) {
                foundSuspiciousActivity = true;

                System.out.println();
                System.out.println("Possible Suspicious Activity Detected");
                System.out.println("Source IP: " + ip);
                System.out.println("Target User: " + username);
                System.out.println("Failed Attempts: " + failCount);
                System.out.println("Successful Login After Failures: " + (hadSuccess ? "Yes" : "No"));

                if (hadSuccess) {
                    System.out.println("Conclusion: This looks like a possible brute-force attack that may have succeeded.");
                    System.out.println("Risk Level: HIGH");
                } else {
                    System.out.println("Conclusion: This looks like a possible brute-force attempt.");
                    System.out.println("Risk Level: MEDIUM");
                }
            }
        }

        if (!foundSuspiciousActivity) {
            System.out.println();
            System.out.println("No suspicious login patterns were detected.");
        }
    }
}