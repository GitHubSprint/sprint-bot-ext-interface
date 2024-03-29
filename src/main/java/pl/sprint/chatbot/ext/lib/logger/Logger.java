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


public final class Logger
{
	private static Logger instance;
	private static final Object m_oPadLock = new Object();
        private long logFileSize = 100000000;
        private String logFilePath = "logs/";       
	private int logLevel = 1;	
	private String logFileName = "sprintbot.ext.lib"; 	
	private static final Event<logMessageEventDelegate> logMessageEvent = new Event<logMessageEventDelegate>();
        

	private static String getCurrentTimeStamp() {
		SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");//dd/MM/yyyy
		Date now = new Date();
		return sdfDate.format(now);
	}
        
        
	public void setLogger(String logFileName) {
		this.logFileName = logFileName;				

		boolean folderExists = (new File(logFilePath)).isDirectory();
		if (!folderExists) {
			(new File(logFilePath)).mkdirs();
		}
	}
        
	public void setLogger(int logLevel, String logFilePath, String logFileName, long logFileSize) {
		
		this.logLevel = logLevel;
		this.logFilePath = logFilePath;
		this.logFileName = logFileName;
		this.logFileSize = logFileSize;		

		boolean folderExists = (new File(logFilePath)).isDirectory();
		if (!folderExists) {
			(new File(logFilePath)).mkdirs();
		}
	}

	/** 
	 Private constructor to prevent instance creation
	*/
	private Logger(){}
	
	/** 
	 An LogWriter instance that exposes a single instance
        * @return 
	*/
	public static Logger getInstance() {
			// If the instance is null then create one and init the Queue               
		synchronized (m_oPadLock) {
			if (instance == null) {
				instance = new Logger();
			}
			return instance;
		}
	}
        
        
	public void WriteToLog(String message)
        {
            this.WriteToLog(message, LogMessagePriority.Info);
        }

	// Write message to log file
	public void WriteToLog(String message, LogMessagePriority priority) {
		String os = System.getProperty("os.name").toLowerCase();
		if(os.contains("win")){
			System.out.println(getCurrentTimeStamp() + "  " + message);
		}
		// Send log message via event to parent app
		if (logMessageEvent != null) {
			logMessageEvent.listeners().forEach((listener) -> listener.invoke(getCurrentTimeStamp() + "   " + message));
		}

		// Check if log message should be written to file
		if (priority.getValue() < logLevel) {
			return;
		}

		// Create path & filename
		String logPath = logFilePath;
		

		String logFile = logPath + logFileName + ".log";
		String oldLogFile = logPath + logFileName + ".old";
		long logSize = 0;

		// Get log file size
		try {
			File f = new File(logFile);
			logSize = f.length();
		} catch (Exception e) {}

		// Rollover old log file if size is exceeded
		if (logSize > logFileSize) {
			try {
				new File(oldLogFile).delete();
				Files.move(Paths.get(logFile), Paths.get(oldLogFile));
			} catch (IOException ex) {
				ex.printStackTrace();
				return;
			}
		}

		// Write to log file
		try {
			String toLog = getCurrentTimeStamp() + " " + message + "\n";

			File f = new File(logFile);
			if(!f.exists()){
			  f.createNewFile();
			}

			Files.write(Paths.get(logFile), toLog.getBytes((StandardCharsets.UTF_8)), StandardOpenOption.APPEND);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		
	}
}