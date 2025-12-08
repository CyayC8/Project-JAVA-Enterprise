package be.ucll.jms;

import java.io.Serializable;
//serializable = toestaan dit object om te zetten in een sequence of bytes (for storage in a queue)
//ActiveMQ kan niet objects opslaan enkel bytes

public class MailMessage implements Serializable {

    private final String text;

    public MailMessage(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }


}
