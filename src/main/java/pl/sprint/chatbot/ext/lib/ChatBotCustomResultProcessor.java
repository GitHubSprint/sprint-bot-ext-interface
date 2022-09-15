/*
 * Copyright © 2019 Sprint S.A.
 * Contact: slawomir.kostrzewa@sprint.pl
 */
package pl.sprint.chatbot.ext.lib;

/**
 * Interfejs wymaganay przez Bot.
 * @author Sławomir Kostrzewa
 */
public interface ChatBotCustomResultProcessor {
    /**
     * External lib, custom processor interface. 
     * @param session chat session itentity
     * @param parameter method parameter. 
     * @param method name of method
     * @return 
     */
    public String processCustomResultPocessor(String session, String parameter, String method);
    
    /**
     * Required to set logname, please add this method to Constructor.
     * Example: Logger.getInstance().setLogger(logname);
     * @param logname 
     */
    public void setLogger(String logname);    
    /**
     * Log message 
     * Example: Logger.getInstance().WriteToLog("Main " + session + " : " + message);
     * @param message message to log.
     * @param session  chat session id.
     */       
    public void log(String message, String session);    
}
