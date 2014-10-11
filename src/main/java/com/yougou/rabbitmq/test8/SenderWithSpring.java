package com.yougou.rabbitmq.test8;  

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SenderWithSpring {
	public static void main(String[] args) throws Exception {
		AbstractApplicationContext ctx = 
				new ClassPathXmlApplicationContext("rabbitmq.xml");
		RabbitTemplate template = ctx.getBean(RabbitTemplate.class);
		template.convertAndSend("Hello, world!");
		Thread.sleep(1000);
		ctx.destroy();
	}
}
