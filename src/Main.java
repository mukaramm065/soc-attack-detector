import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        // I put the logs in a separate text file so the program feels more real.
        String filePath = "logs/auth_logs.txt";

        try {
            // Read all lines from the log file.
            List<String> lines = Files.readAllLines(Paths.get(filePath));

            System.out.println("Reading logs from: " + filePath);
            System.out.println("Total log lines found: " + lines.size());
            System.out.println();

            // Parse the raw text lines into LogEvent objects.
            List<LogEvent> events = LogParser.parseLogs(lines);

            System.out.println("Parsed events: " + events.size());
            System.out.println();

            // for showing logs are actually being parsed correctly.
            System.out.println("Parsed Log Events");
            System.out.println("-----------------");

            for (LogEvent event : events) {
                System.out.println(event);
                System.out.println();
            }

            // Analyze the parsed logs for suspicious behavior.
            AttackDetector.analyzeLogs(events);

        } catch (IOException e) {
            System.out.println("Error reading the log file.");
            System.out.println("Please make sure the file exists at: " + filePath);
            e.printStackTrace();
        }
    }
}
