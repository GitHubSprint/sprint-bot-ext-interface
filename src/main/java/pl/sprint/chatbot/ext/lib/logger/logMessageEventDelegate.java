package pl.sprint.chatbot.ext.lib.logger;


@FunctionalInterface
public interface logMessageEventDelegate
{
    void invoke(String logMessage);
}