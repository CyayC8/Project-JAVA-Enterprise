package be.ucll.jms;

import jakarta.mail.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class MailListener {

    @Autowired
    private JavaMailSender mailSender;

    @JmsListener(destination = "mailQueue")
    //register this message as a listener for the destination "mailQueue"

    public void receiveMessageAndSendEmail(List<OrderEmailDTO> ordersFromQueue) {

        StringBuilder body = new StringBuilder();

        body.append("<h2> Overzicht van uw bestellingen </h2>");
        body.append("<table border = '1' style='border-collapse: collapse;'>");
        body.append("<tr><th>Order ID</th><th>Aantal Producten</th><th>Totale Prijs</th><th>Afgeleverd</th></tr>");

        for (OrderEmailDTO dto : ordersFromQueue) {
            body.append("<tr>")
                    .append("<td>").append(dto.getOrderId()).append("</td>")
                    .append("<td>").append(dto.getAantalProducten()).append("</td>")
                    .append("<td>").append(dto.getTotaalBedrag()).append("</td>")
                    .append("<td>").append(dto.getAfgeleverd() ? "Ja" : "Nee").append("</td>")
                    .append("</tr>");
        }
        body.append("</table>");

        String to = ordersFromQueue.getFirst().getCustomerEmail();
        String subject = "Bestellingen overzicht";
        String html = body.toString();

        MimeMessagePreparator message = mimeMessage -> {
            mimeMessage.setRecipient(Message.RecipientType.TO, new jakarta.mail.internet.InternetAddress(to));
            mimeMessage.setSubject(subject);
            mimeMessage.setContent(html, "text/html");
        };


        try {
            mailSender.send(message);
            System.out.println("SMTP SUCCESS");
        } catch (Exception ex) {
            System.out.println("SMTP ERROR: " + ex.getMessage());
            ex.printStackTrace();
        }




    }
}

