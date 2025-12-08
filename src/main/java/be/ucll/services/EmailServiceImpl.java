package be.ucll.services;


import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional //why?
public class EmailServiceImpl implements EmailService{
}
