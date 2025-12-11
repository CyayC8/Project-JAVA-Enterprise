package be.ucll.services;


import be.ucll.jms.MailProducer;
import be.ucll.jms.OrderEmailDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmailServiceImpl implements EmailService {

    private final MailProducer mailProducer;

    public EmailServiceImpl(MailProducer mailProducer) {
        this.mailProducer = mailProducer;
    }

    @Override
    public void sendEmail(List<OrderEmailDTO> ordersToQueue) {

        mailProducer.sendMessage(ordersToQueue);
    }
}
