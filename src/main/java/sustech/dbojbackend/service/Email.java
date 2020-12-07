package sustech.dbojbackend.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import sustech.dbojbackend.model.data.User;

import javax.annotation.Resource;

@Slf4j
@Service
public class Email {
    @Resource
    private Token tokenResource;
    @Resource
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String emailSendAddress;

    public void sendEmailToResetPassword(String Email, User user) {
        var msg = new SimpleMailMessage();
        log.info("---");
        log.info(emailSendAddress);
        log.info("---");
        System.out.println(emailSendAddress);
        msg.setBcc();
        msg.setTo(Email);
        msg.setSubject("Click Link to Reset Password");
        msg.setText(tokenResource.createToken(user));
        try {
            mailSender.send(msg);
        } catch (MailException ex) {
            System.err.println(ex.getMessage());
        }
    }

}
