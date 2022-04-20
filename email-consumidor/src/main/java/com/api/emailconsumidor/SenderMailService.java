package com.api.emailconsumidor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class SenderMailService {

    @Autowired
    private JavaMailSender mailSender;

    void enviar(String emailDestinario, String texto, String subject) {
        SimpleMailMessage email = new SimpleMailMessage();
        email.setFrom("154154marcia@gmail.com");      
        email.setTo(emailDestinario);
        email.setSubject(subject);
        email.setText(texto);
        mailSender.send(email);

    }
}