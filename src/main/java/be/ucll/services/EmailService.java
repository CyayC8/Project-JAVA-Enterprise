package be.ucll.services;

import be.ucll.jms.OrderEmailDTO;

import java.util.List;

public interface EmailService {

    void sendEmail(List<OrderEmailDTO> ordersToQueue);
}
