package com.yougou.rabbitmq.test2;  

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

/**
 * ClassName: Worker
 * Desc: C端消费者
 * date: 2014-10-11 上午9:47:31
 * @author li.n1 
 * @since JDK 1.6
 */
public class Worker {
	private static final String TASK_QUEUE_NAME = "task_queue";
	public static void main(String[] args) throws Exception {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		//声明此队列并且持久化  
		channel.queueDeclare(TASK_QUEUE_NAME, true, false, false, null);  
		System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
		channel.basicQos(1);//告诉RabbitMQ同一时间给一个消息给消费者
		QueueingConsumer consumer = new QueueingConsumer(channel); 
		/* 
        把名字为TASK_QUEUE_NAME的Channel的值回调给QueueingConsumer,即使一个worker在处理消息的过程中停止了，这个消息也不会失效 
      */  
		channel.basicConsume(TASK_QUEUE_NAME, false, consumer); 
		while (true) {  
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();//得到消息传输信息  
            String message = new String(delivery.getBody());  
  
            System.out.println(" [x] Received '" + message + "'");  
            doWork(message);  
            System.out.println(" [x] Done");  
  
            channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);//下一个消息  
        }  
	}
	
	private static void doWork(String task) throws InterruptedException {  
        for (char ch : task.toCharArray()) {  
            if (ch == '.')  
                Thread.sleep(1000);//这里是假装我们很忙  
        }  
    } 
}
