package com.yougou.rabbitmq.test8;  

import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;

public class SenderWithJava {
	public static void main(String[] args) throws Exception {
		ConnectionFactory cf = new CachingConnectionFactory();
		RabbitAdmin admin = new RabbitAdmin(cf);
		Queue queue = new Queue("myQueue");
		admin.declareQueue(queue);
		TopicExchange exchange = new TopicExchange("myExchange");
		admin.declareExchange(exchange);
		admin.declareBinding(
				BindingBuilder.bind(queue)
				.to(exchange).with("foo.*")
				);
		SimpleMessageListenerContainer container =
	            new SimpleMessageListenerContainer(cf);
		Object listener = new Object() {
	        public void handleMessage(String foo) {
	            System.out.println(foo);
	        }
	    };
	    MessageListenerAdapter adapter = new MessageListenerAdapter(listener);
	    container.setMessageListener(adapter);
	    container.setQueueNames("myQueue");
	    container.start();
	    
	    // send something
	    RabbitTemplate template = new RabbitTemplate(cf);
	    template.convertAndSend("myExchange", "foo.bar", "Hello, world!");
	    Thread.sleep(1000);
	    container.stop();
	}
}
