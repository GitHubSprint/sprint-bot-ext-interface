package pl.sprint.chatbot.ext.lib.logger;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Custom Logger - using sample
 * Logger.getInstance().setLogger(1, "C:/Logs/", "testLogger", 10000000);
 * Logger.getInstance().WriteToLog("Class", "String to log", LogMessagePriority.Info);
*/

public final class Logger {
    private static Logger instance;
    private static final Object m_oPadLock = new Object();
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    private static final String LOG_EXTENSION = ".log";
    private static final String OLD_LOG_EXTENSION = ".old";

    private long logFileSize = 100000000;
    private String logFilePath = "logs/";
    private int logLevel = 1;
    public static String logFileName = "sprintbot.ext.lib";
    private static final Event<logMessageEventDelegate> logMessageEvent = new Event<>();

    private static String getCurrentTimeStamp() {
        return DATE_FORMAT.format(new Date());
    }

    public void setLogger(String logName) {
        logFileName = logName;
        ensureLogDirectoryExists();
    }

    public void setLogger(int logLevel, String logFilePath, String logName, long logFileSize) {
        this.logLevel = logLevel;
        this.logFilePath = logFilePath;
        logFileName = logName;
        this.logFileSize = logFileSize;
        ensureLogDirectoryExists();
    }

    private void ensureLogDirectoryExists() {
        Path path = Paths.get(logFilePath);
        if (!Files.isDirectory(path)) {
            try {
                Files.createDirectories(path);
            } catch (IOException ignored) {}
        }
    }

    private Logger() {}

    public static Logger getInstance() {
        if (instance == null) {
            synchronized (m_oPadLock) {
                if (instance == null) {
                    instance = new Logger();
                }
            }
        }
        return instance;
    }

    public void WriteToLog(String message) {
        WriteToLog(message, LogMessagePriority.Info);
    }

    public void WriteToLog(String message, LogMessagePriority priority) {
        String timestamp = getCurrentTimeStamp();
        String formattedMessage = timestamp + " " + message;

        // Console output for Windows
        if (System.getProperty("os.name").toLowerCase().contains("win")) {
            System.out.println(formattedMessage);
        }

        // Event notification
        if (logMessageEvent != null) {
            logMessageEvent.listeners().forEach(listener -> listener.invoke(formattedMessage));
        }

        // Skip file logging if priority is below threshold
        if (priority.getValue() < logLevel) {
            return;
        }

        writeToLogFile(formattedMessage);
    }

    private void writeToLogFile(String message) {
        Path logPath = Paths.get(logFilePath + logFileName + LOG_EXTENSION);
        Path oldLogPath = Paths.get(logFilePath + logFileName + OLD_LOG_EXTENSION);

        // Rollover if size exceeded
        try {
            if (Files.exists(logPath) && Files.size(logPath) > logFileSize) {
                Files.deleteIfExists(oldLogPath);
                Files.move(logPath, oldLogPath);
            }
        } catch (IOException ignored) {}

        // Write to log file
        try {
            Files.writeString(logPath, message + "\n",
                    StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException ignored) {}
    }
}