package com.api.estado.publicadorRabbit;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 
 * @author duda
 *
 * Criar um Bean para o envio de mensagem
 */

@Configuration
public class SenderConfig {

    @Value("${queue.name_queue_duda}")
    private String message;

    @Bean
    public Queue queue() {
        return new Queue(message, true);
    }

}