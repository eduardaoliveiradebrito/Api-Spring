package com.api.emailconsumidor;


import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
/**
 * 
 * @author duda
 *
 * Eviar as mensagens para o Rabbit
 */

@Component
public class QueueConsumer {
	
	@Autowired
	private SenderMailService service;
	
	@RabbitListener(queues = { "${queue.name_queue_duda}" })
	//@EventListener(ApplicationReadyEvent.class)
	
	public void triggerMail(@Payload String fileBody) {
		service.enviar("154154marcia@gmail.com", fileBody, "Cadastro/ Atualização de um novo estado");
		System.out.println("Message " + fileBody);
		
	}

}