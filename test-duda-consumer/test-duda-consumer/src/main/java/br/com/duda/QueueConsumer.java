package br.com.duda;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class QueueConsumer {

	@RabbitListener(queues = { "${queue.name_queue_duda}" })
	public void receive(@Payload String fileBody) {
		System.out.println("Message " + fileBody);
	}

}
