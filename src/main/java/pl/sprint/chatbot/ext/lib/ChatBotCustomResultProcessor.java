/*
 * Copyright © 2025 Sprint S.A.
 * Contact: slawomir.kostrzewa@sprint.pl
 */
package pl.sprint.chatbot.ext.lib;

import pl.sprint.chatbot.ext.lib.logger.ProcessorLogger;
import java.util.Locale;

public interface ChatBotCustomResultProcessor {

    /**
     * Metoda opakowująca z logiką mierzenia czasu. Wywołuje metodę doProcessing.
     * To jest metoda, której powinni używać klienci interfejsu.
     * @param session chat session identity
     * @param parameter method parameter. 
     * @param method name of method
     * @return String result from custom processor.
     */
    default String processCustomResultPocessor(String session, String parameter, String method) {
        long start = System.nanoTime();
        try {
            return processCustomResultProcessor(session, parameter, method);
        } finally {
            double durationInSeconds = (System.nanoTime() - start) / 1_000_000_000.0;
            ProcessorLogger.info(String.format(Locale.of("Polish"),
                    "%s : %s [%.5f]",
                    session,
                    method,
                    durationInSeconds));
        }
    }

    /**
     * Metoda do zaimplementowania przez klasy klienckie. Zawiera logikę biznesową.
     */
    String processCustomResultProcessor(String session, String parameter, String method);
    
    /**
     * Clear session data.
     * Example: ChatBotCustomResultProcessor.getInstance().clear(session);
     * @param session chat session id.
     */
    void clear(String session);
    /**
     * Required to set logname, please add this method to Constructor.
     * Example: Logger.getInstance().setLogger(logname);
     * @param logname log file name.
     */
    void setLogger(String logname);
    /**
     * Log message 
     * Example: Logger.getInstance().WriteToLog("Main " + session + " : " + message);
     * @param message message to log.
     * @param session  chat session id.
     */
    void log(String message, String session);
}
