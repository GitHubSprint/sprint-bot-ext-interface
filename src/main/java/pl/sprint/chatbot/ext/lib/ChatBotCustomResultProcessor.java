/*
 * Copyright © 2025 Sprint S.A.
 * Contact: slawomir.kostrzewa@sprint.pl
 */
package pl.sprint.chatbot.ext.lib;

public interface ChatBotCustomResultProcessor {
    /**
     * External lib, custom processor interface. 
     * @param session chat session identity
     * @param parameter method parameter. 
     * @param method name of method
     * @return String result from custom processor.
     */
    String processCustomResultPocessor(String session, String parameter, String method);

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
