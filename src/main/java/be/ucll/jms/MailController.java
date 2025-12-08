package be.ucll.jms;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MailController {

    private final MailProducer mailProducer;

    public MailController(MailProducer mailProducer) {
        this.mailProducer = mailProducer;
    }

    @PostMapping("/publish-message")
    public ResponseEntity<String> publishMessage(@RequestBody String mailMessage){

        MailMessage message = new MailMessage(mailMessage);
        try{
            mailProducer.sendMessage(message.getText());
            return ResponseEntity.ok("Message published successfully");
        } catch (Exception e){
            return ResponseEntity.badRequest().body("Message could not be published");
        }


    }
}
