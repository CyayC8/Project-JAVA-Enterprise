package be.ucll.jms;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component //Spring detecteert and registered as a BEAN hierdoor kan Spring dit injecteren in  andere beans zoals EMailServiceImpl
public class MailProducer {

    private final JmsTemplate jmsTemplate;
    //provides convenient methoids for sending and receiving messages

    public MailProducer(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public void sendMessage(List<OrderEmailDTO> ordersToQueue) {
        jmsTemplate.convertAndSend("mailQueue", ordersToQueue);
    }
    //deze fucntie zet het hele DTO object op de queue "mailQueue"


}
