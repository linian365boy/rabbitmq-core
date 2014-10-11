package com.yougou.rabbitmq.test4;  

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

public class ReceiveLogsDirectTwo {
	private static final String EXCHANGE_NAME = "direct_logs";//定义Exchange名称  
	  
    public static void main(String[] argv) throws Exception { 
    	 ConnectionFactory factory = new ConnectionFactory();  
         factory.setHost("localhost");  
         Connection connection = factory.newConnection();  
         Channel channel = connection.createChannel();  
   
         channel.exchangeDeclare(EXCHANGE_NAME, "direct");//声明Exchange  
   
         String queueName = "queue_logs2";//定义队列名为“queue_logs1”的Queue 
         channel.queueDeclare(queueName, false, false, false, null);  
         String routingKeyOne = "error";//"error"路由规则  
         channel.queueBind(queueName, EXCHANGE_NAME, routingKeyOne);//把Queue、Exchange及路由绑定 
         String routingKeyTwo = "all";  
         channel.queueBind(queueName, EXCHANGE_NAME, routingKeyTwo);  
         System.out.println(" [*] Waiting for messages.");  
         QueueingConsumer consumer = new QueueingConsumer(channel);  
         channel.basicConsume(queueName, true, consumer); 
         while (true) {  
             QueueingConsumer.Delivery delivery = consumer.nextDelivery();  
             String message = new String(delivery.getBody());  
             String routingKey = delivery.getEnvelope().getRoutingKey();  
   
             System.out.println(" [x] Received '" + routingKey + "':'" + message  
                     + "'");  
         }  
    }
}
