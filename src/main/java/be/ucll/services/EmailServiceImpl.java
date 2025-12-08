package be.ucll.services;


import be.ucll.jms.OrderEmailDTO;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional //why?
public class EmailServiceImpl implements EmailService{

    @Override
    public void sendEmail(OrderEmailDTO orderEmailDTO) {



    }
}
