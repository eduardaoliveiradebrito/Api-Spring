package com.api.consumer.publicadorExchange;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/testeExchange")
public class TesteControllerExchange {

    public TesteControllerExchange(AmqpTemplate queueSender) {
        this.queueSender = queueSender;
    }

    private final AmqpTemplate queueSender;

    @GetMapping
    public String send(){

        String mensagem = "test message";

        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setHeader("ultima", "sim");
        Message message = new Message(mensagem.getBytes(), messageProperties);

        queueSender.convertAndSend("teste-exchange", "routing-key-teste", message);
        return "true 02";
    }

}