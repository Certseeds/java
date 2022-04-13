package dbojbackend.service;

import dbojbackend.model.data.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Slf4j
@Service
public class Email {
    private static final String SubjectReset = "Click Link to Reset Password";
    private static final String SubjectUpLevel = "Click Link to UpLevel Your Account";
    @Value("${spring.mail.username}")
    public String emailSendAddress;
    @Resource
    private Token tokenResource;
    @Resource
    private JavaMailSender mailSender;

    private SimpleMailMessage getSimpleMailMessage(User user, String subject) {
        var msg = new SimpleMailMessage();
        System.out.println(emailSendAddress);
        msg.setFrom(emailSendAddress);
        msg.setBcc();
        msg.setTo(user.getEmail());
        msg.setSubject(subject);
        return msg;
    }

    public void sendEmailToResetPassword(User user, String newPassword) {
        SimpleMailMessage msg = getSimpleMailMessage(user, SubjectReset);
        msg.setText(String.format("%s%s", "localhost:8888/login/reset?token=", tokenResource.createTokenWithNewPassWord(user, newPassword)));
        try {
            mailSender.send(msg);
        } catch (MailException ex) {
            System.err.println(ex.getMessage());
        }
    }


    public void sendEmailToupLevel(User user, String token) {
        SimpleMailMessage msg = getSimpleMailMessage(user, SubjectUpLevel);
        msg.setText(String.format("%s%s", "localhost:8888/upLevel?token=", token));
        try {
            mailSender.send(msg);
        } catch (MailException ex) {
            System.err.println(ex.getMessage());
        }
    }
}
