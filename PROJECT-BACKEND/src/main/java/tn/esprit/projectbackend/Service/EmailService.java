package tn.esprit.projectbackend.Service;

import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;


    public String sendAttachmentEmail(String ReciverEmail/*, byte[] pdfBytes, String filename*/ ) throws jakarta.mail.MessagingException {

        MimeMessage message = mailSender.createMimeMessage();

        boolean multipart = true;

        MimeMessageHelper helper = new MimeMessageHelper(message, multipart, "utf-8");

        String htmlMsg = "<h3>your study is ready   </h3>"
                + "<img src=https://drive.google.com/file/d/18ZAJsyob8ZXEiNe0G4lg_Ad3m8rg2zzU/view?usp=sharing>";


        message.setContent(htmlMsg, "text/html");
        helper.setTo(ReciverEmail);
        helper.setSubject("CrediFlex Financial Study ");
        this.mailSender.send(message);
        // helper.addAttachment(filename, new ByteArrayResource(pdfBytes));



        return ("Email Send ! ");
    }
}