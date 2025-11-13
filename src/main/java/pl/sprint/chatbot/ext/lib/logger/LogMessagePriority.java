package pl.sprint.chatbot.ext.lib.logger;


import java.util.HashMap;
import java.util.Map;

public enum LogMessagePriority {
    Emergency(5),
    Error(4),
    Info(2),
    Debug(1);

    private static final Map<Integer, LogMessagePriority> VALUE_MAP = new HashMap<>();

    static {
        for (LogMessagePriority priority : values()) {
            VALUE_MAP.put(priority.intValue, priority);
        }
    }

    private final int intValue;

    LogMessagePriority(int value) {
        this.intValue = value;
    }

    public int getValue() {
        return intValue;
    }

    public static LogMessagePriority forValue(int value) {
        return VALUE_MAP.get(value);
    }
}