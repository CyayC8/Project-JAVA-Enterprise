package be.ucll.jms;

import jakarta.jms.Message;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class MailListener {

    @JmsListener(destination = "mailQueue")
    //register this message as a listener for the destination "mailQueue"

    public void receiveMessage(Message message){
        System.out.println("Received :" + message);
    }

}
