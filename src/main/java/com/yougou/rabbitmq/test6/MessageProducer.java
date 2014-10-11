package com.yougou.rabbitmq.test6;  

import org.springframework.amqp.rabbit.core.RabbitTemplate;

public class MessageProducer{
	private RabbitTemplate rabbitTemplate;
	public void sendMessage(Integer i){
		String message = "Hello World wubin " + "#" + i;
		//Exchange的名称为"hello.topic"，routingkey的名称为"hello.world.q123ueue" 
		rabbitTemplate.convertAndSend("hello.topic","hello.world.q123ueue",message);
		System.out.println("发送第" + i + "个消息成功！内容为：" + message); 
	}
	public RabbitTemplate getRabbitTemplate() {
		return rabbitTemplate;
	}
	public void setRabbitTemplate(RabbitTemplate rabbitTemplate) {
		this.rabbitTemplate = rabbitTemplate;
	}
}
