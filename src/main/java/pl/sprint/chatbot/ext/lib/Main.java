package pl.sprint.chatbot.ext.lib;

/**
 * Example class
 */
public class Main implements ChatBotCustomResultProcessor {

    public static void main(String[] args) {
        Main m = new Main();
        m.processCustomResultPocessor("sesja-testowa-123", "parametr-abc", "mojaMetoda");
    }

    @Override
    public String processCustomResultProcessor(String session, String parameter, String method) {
        // Tutaj jest właściwa logika biznesowa, która ma być zmierzona.
        try {
            // Symulacja jakiejś pracy
            Thread.sleep(150);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return "OK";
    }

    @Override
    public void clear(String session) {

    }

    @Override
    public void setLogger(String logname) {

    }

    @Override
    public void log(String message, String session) {

    }
}
