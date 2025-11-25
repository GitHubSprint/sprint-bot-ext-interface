package pl.sprint.chatbot.ext.lib.logger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ProcessorLogger {
    private static final String LOG_FILE = "logs/" + Logger.logFileName + ".time.processor.log";
    private static final String OLD_LOG_FILE = "logs/plugin.time.processor.log.old";
    private static final long MAX_FILE_SIZE = 100 * 1024 * 1024;

    private static final DateTimeFormatter DATE_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private static final Object LOCK = new Object();

    private ProcessorLogger() {}

    public static void info(String message) {
        log(message, null);
    }

    public static void error(String message, Throwable t) {
        log("ERROR " +  message, t);
    }

    private static void log(String message, Throwable t) {
        String timestamp = LocalDateTime.now().format(DATE_FORMATTER);
        String logEntry = String.format("%s %s",
                timestamp, message);

        synchronized (LOCK) {
            checkAndRotate();
            writeToFile(logEntry, t);
        }
    }

    private static void checkAndRotate() {
        File currentFile = new File(LOG_FILE);
        if (!currentFile.exists()) {
            return;
        }
        if (currentFile.length() >= MAX_FILE_SIZE) {
            File oldFile = new File(OLD_LOG_FILE);

            if (oldFile.exists()) {
                if (!oldFile.delete()) {
                    System.err.println("Nie udało się usunąć starego loga: " + OLD_LOG_FILE);
                }
            }
            if (!currentFile.renameTo(oldFile)) {
                System.err.println("Nie udało się zrotować loga (rename failed).");
            }
        }
    }

    private static void writeToFile(String logEntry, Throwable t) {
        try (FileWriter fw = new FileWriter(LOG_FILE, true);
             PrintWriter pw = new PrintWriter(fw)) {

            pw.println(logEntry);
            if (t != null) {
                t.printStackTrace(pw);
            }

        } catch (IOException e) {
            System.err.println("Błąd krytyczny loggera: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
