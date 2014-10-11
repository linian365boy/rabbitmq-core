package com.yougou.rabbitmq.test3;  

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

/**
 * ClassName: ReceiveLogsOne
 * Desc: C端代码（消费者）
 * date: 2014-10-11 上午11:06:01
 * @author li.n1 
 * @since JDK 1.6
 */
public class ReceiveLogsThree {
	private static final String EXCHANGE_NAME = "logs";
	public static void main(String[] args) throws Exception {
		ConnectionFactory factory = new ConnectionFactory();  
        factory.setHost("localhost");  
        Connection connection = factory.newConnection();  
        Channel channel = connection.createChannel();  
        channel.exchangeDeclare(EXCHANGE_NAME, "fanout"); 
        String queueName = "log-fb3";
        channel.queueDeclare(queueName,false,false,false,null);
        //把Queue、Exchange绑定
        channel.queueBind(queueName, EXCHANGE_NAME, "");
        QueueingConsumer consumer = new QueueingConsumer(channel); 
        channel.basicConsume(queueName, true, consumer);  
        while(true){
        	QueueingConsumer.Delivery delivery = consumer.nextDelivery();
        	String message = new String(delivery.getBody());
        	System.out.println("[x] Received '" + message + "'");
        }
        
	}
}
