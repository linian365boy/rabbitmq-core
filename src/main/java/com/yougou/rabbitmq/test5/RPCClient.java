package com.yougou.rabbitmq.test5;  

import java.util.UUID;

import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

public class RPCClient {
	private Connection connection;
	private Channel channel;
	private String requestQueueName = "rpc_queue";
	private String replyQueueName;
	private QueueingConsumer consumer;
	
	public RPCClient() throws Exception{
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");  
		connection = factory.newConnection();  
        channel = connection.createChannel(); 
        replyQueueName = channel.queueDeclare().getQueue();
        System.out.println("replyQueueName=="+replyQueueName);
        consumer = new QueueingConsumer(channel); 
        channel.basicConsume(replyQueueName, true, consumer);
	}
	
	@SuppressWarnings("deprecation")
	public String call(String message) throws Exception{
		String response = null;
		String corrId = UUID.randomUUID().toString();
		BasicProperties props = new BasicProperties();
		props.setReplyTo(replyQueueName);  
        props.setCorrelationId(corrId);
        channel.basicPublish("", requestQueueName, props, message.getBytes());
        while (true) {  
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();  
            if (delivery.getProperties().getCorrelationId().equals(corrId)) {  
                response = new String(delivery.getBody(), "UTF-8");  
                break;  
            }  
        }  
        return response;
	}
	
	public void close() throws Exception{
		connection.close();
	}
	public static void main(String[] args) {
		RPCClient fibonacciRpc = null;  
        String response = null;  
        try {
            fibonacciRpc = new RPCClient();  
            System.out.println(" [x] Requesting fib(30)");  
            response = fibonacciRpc.call("30");  
            System.out.println(" [.] Got '" + response + "'");  
            System.out.println(" [x] Requesting fib(-1)");  
            response = fibonacciRpc.call("-1");  
            System.out.println(" [.] Got '" + response + "'");  
            System.out.println(" [x] Requesting fib(a)");  
            response = fibonacciRpc.call("a");  
            System.out.println(" [.] Got '" + response + "'");  
        } catch (Exception e) {  
            e.printStackTrace();  
        } finally {  
            if (fibonacciRpc != null) {  
                try {  
                    fibonacciRpc.close();  
                } catch (Exception ignore) {  
                }  
            }  
        } 
	}
}
