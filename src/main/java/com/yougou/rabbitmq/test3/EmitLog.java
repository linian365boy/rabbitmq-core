package com.yougou.rabbitmq.test3;  

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
/**
 * ClassName: EmitLog
 * Desc: P端代码（生产者）
 * date: 2014-10-11 上午11:05:18
 * @author li.n1 
 * @since JDK 1.6
 */
public class EmitLog {
	private static final String EXCHANGE_NAME = "logs";
	public static void main(String[] args) throws Exception{
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		//声明Exchange
		//Exchange的类型有：direct , topic , headers 和 fanout
		channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
		for(int i=0;i<=2;i++){
			String message = "hello world "+i;
			channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes());
			System.out.println(" [x] Sent '" + message + "'");
		}
		channel.close();
		connection.close();
	}
}
