package be.ucll.jms;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component //why? -- it should be detected and registered as a BEAN
public class MailProducer {

    private final JmsTemplate jmsTemplate;
    //provides convenient methoids for sending and receiving messages

    public MailProducer(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public void sendMessage(String mailMessage){
        jmsTemplate.convertAndSend("mailQueue", mailMessage);
    }


}
