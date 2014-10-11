package com.yougou.rabbitmq.test7;  

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;



public class Sender {
	public static void main(String[] args) {
		ConnectionFactory connectionFactory = new CachingConnectionFactory();
		AmqpAdmin admin = new RabbitAdmin(connectionFactory);
		admin.declareQueue(new Queue("myqueue"));
		
		AmqpTemplate template = new RabbitTemplate(connectionFactory);
		template.convertAndSend("myqueue","foo");
		
		String foo = (String)template.receiveAndConvert("myqueue");
	}
}
