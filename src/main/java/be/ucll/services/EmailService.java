package be.ucll.services;

import be.ucll.jms.OrderEmailDTO;

public interface EmailService {

    void sendEmail(OrderEmailDTO orderEmailDTO);
}
