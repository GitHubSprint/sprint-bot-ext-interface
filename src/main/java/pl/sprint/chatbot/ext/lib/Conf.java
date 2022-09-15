/*
 * Copyright © 2021 Sprint S.A.
 * Contact: slawomir.kostrzewa@sprint.pl

 */
package pl.sprint.chatbot.ext.lib;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;
import pl.sprint.chatbot.ext.lib.logger.LogMessagePriority;
import pl.sprint.chatbot.ext.lib.logger.Logger;

/**
 *
 * @author Sławomir Kostrzewa
 */
public class Conf {
    

    private static Properties appProps; 

    // flaga czy system został zainicjalizowany
    private static boolean serviceInitialized = false;
    
    
    public static String getValue(String key, String defaultValue)
    {
        String ret = defaultValue; 
        if(appProps == null)
        {            
            Logger.getInstance().WriteToLog("Config appProps is null, return default value" + defaultValue);
            return defaultValue; 
        }
        
        try {
            ret = appProps.getProperty(key);
            if(ret == null)
                ret = defaultValue; 
        } catch (Exception e) {
            Logger.getInstance().WriteToLog("Config getValue exception message: " + e.getMessage(), LogMessagePriority.Error);            
        }
        
        Logger.getInstance().WriteToLog("Config getValue key: " + key + " value: " + ret);
        
        return ret;
    }
    
    
    public static void reConfigure(String confFileName)
    {
        serviceInitialized = false;
        configure(confFileName);
    }
    
    public static void configure(String confFileName)
    {
         Logger.getInstance().WriteToLog("Config configure " + serviceInitialized);
        if (serviceInitialized)
            return;	
		// init configuration    
               
       appProps = new Properties();
        try {
            appProps.load(Files.newInputStream(Paths.get("config/plugins/" + confFileName)));
        } catch (Exception ex) {
            Logger.getInstance().WriteToLog("Config configure exception message: " + ex.getMessage(), LogMessagePriority.Error);
            appProps = null;
        }
        
    }
    
   
}
