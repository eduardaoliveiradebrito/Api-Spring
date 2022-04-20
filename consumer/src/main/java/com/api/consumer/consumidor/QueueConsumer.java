package com.api.consumer.consumidor;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

/**
 * 
 * @author duda
 *
 * Consome as mensagens que vem do Rabbit
 */

@Component
public class QueueConsumer {

	@RabbitListener(queues = { "${queue.name_queue_duda}" })
    public void receive(@Payload String fileBody) {
        System.out.println("Message " + fileBody);
    }

}
