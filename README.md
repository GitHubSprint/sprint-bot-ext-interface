# External SprintBot plugin interface and logger

## Usage - Build Yourself

1. Build using [Maven](http://maven.apache.org) to your local Maven repository:

        mvn -DskipTests install

2. Use in your Maven project:

        <!-- https://github.com/GitHubSprint/sprint-bot-ext-interface -->         
        <dependency>
            <groupId>pl.sprint</groupId>
            <artifactId>sprint-bot-ext-interface</artifactId>
            <version>1.0.1</version>            
        </dependency>

3. Usage examle

    Create e.g Main class. 
    <code>

    import pl.sprint.chatbot.ext.lib.logger.Logger;

    import pl.sprint.sprint.chatbot.ext.lib.ChatBotCustomResultProcessor;

    //Add Constructor and impletment Interface and all requied methods.
    public class Main implements ChatBotCustomResultProcessor
    {
        //setLogger required in Constructor
        public Main() 
        {    
            setLogger("sprintbot.ext.lib");
            
        }

        @Override
        public String processCustomResultPocessor(String session, String parameter, String method) 
        {
            log("parameter: " + parameter + " method: " + method, session);
        }
        @Override
        public void setLogger(String logname) {
            Logger.getInstance().setLogger(logname);
        }

        @Override
        public void log(String message, String session) {
            Logger.getInstance().WriteToLog("Main " + session + " : " + message);
        }
    }
    </code>