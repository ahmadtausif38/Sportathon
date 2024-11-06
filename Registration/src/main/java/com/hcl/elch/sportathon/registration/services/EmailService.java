package com.hcl.elch.sportathon.registration.services;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    private static final Logger log= LogManager.getLogger(EmailService.class);

    private String loadEmailTemplate() throws IOException {
        log.debug("loading Email Template....");

        ClassPathResource resource = new ClassPathResource("templates/registration-mail.html");

        byte[] fileBytes = FileCopyUtils.copyToByteArray(resource.getInputStream());

        return new String(fileBytes, StandardCharsets.UTF_8);

    }
    public void mailCreation(String email, String emailContent, String sub) throws MessagingException{

        log.info("Mail is creating...");

        MimeMessage emailMessage = javaMailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(emailMessage, true);

        helper.setSubject(sub);

        helper.setTo(email);

        helper.setText(emailContent, true);

        javaMailSender.send(emailMessage);
        log.info("Mail sent ");

    }
    public void mailSendingForRegistration(String email,String username, String gameName) throws IOException, MessagingException {

        log.info("Inside the mailSendingForRegistration() method now........");

            String htmlTemplate = loadEmailTemplate();

            String emailContent = htmlTemplate
                    .replace("{name}", username)
                    .replace("{game}", gameName);

            String subject = "Sport Registration Confirmation";

            mailCreation(email, emailContent, subject);

            log.info("Message sent successfully for Registration to :- {}", email);

        }



}
