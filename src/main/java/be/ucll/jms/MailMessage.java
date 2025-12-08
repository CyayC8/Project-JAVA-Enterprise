package be.ucll.jms;

import java.io.Serializable;
//serializable = toestaan dit object om te zetten in een sequence of bytes (for storage in a queue)
//ActiveMQ kan niet objects opslaan enkel bytes

public class MailMessage implements Serializable {

    public MailMessage() {

    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    private String to;
    private String subject;
    private String body;

    public boolean isHtml() {
        return html;
    }

    public void setHtml(boolean html) {
        this.html = html;
    }

    private boolean html;


    public MailMessage(String to, String subject, String body) {
        this.to = to;
        this.subject = subject;
        this.body = body;
        this.html = true;
    }


}
