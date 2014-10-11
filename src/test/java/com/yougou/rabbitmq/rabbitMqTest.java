package com.yougou.rabbitmq;  

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:application-rabbitmq.xml"})
public class rabbitMqTest {
	@Autowired
	private AmqpTemplate jmsTemplate;
	
	@Test
	public void test1() throws Exception{
		jmsTemplate.convertAndSend("hello, world");
		Thread.sleep(1000);
	}

	public AmqpTemplate getJmsTemplate() {
		return jmsTemplate;
	}

	public void setJmsTemplate(AmqpTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}
}
