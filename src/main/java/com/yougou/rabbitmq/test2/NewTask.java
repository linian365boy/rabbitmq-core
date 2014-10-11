package com.yougou.rabbitmq.test2;  

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

/**
 * ClassName: NewTask
 * Desc: P端产生消息（生产者）
 * date: 2014-10-11 上午9:47:09
 * @author li.n1 
 * @since JDK 1.6
 */
public class NewTask {
	private static final String TASK_QUEUE_NAME = "task_queue";
	public static void main(String[] args) throws Exception {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost"); 
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		
		//声明此队列并且持久化
		channel.queueDeclare(TASK_QUEUE_NAME,true,false,false,null);
		String message = getMessage(args);
		//持久化消息//发送消息  
		channel.basicPublish("", TASK_QUEUE_NAME,  
                MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
		
		System.out.println(" [x] Sent '" + message + "'");  
	    channel.close();
	    connection.close();
	}
	/** 
	 * getMessage:得到信息 
	 * @author li.n1 
	 * @param args
	 * @return 
	 * @since JDK 1.6 
	 */  
	private static String getMessage(String[] args) {
		if(args.length<1){
			return "Hello World";
		}
		return joinStrings(args," ");
	}
	/** 
	 * joinStrings: 
	 * @author li.n1 
	 * @param args
	 * @param string
	 * @return 
	 * @since JDK 1.6 
	 */  
	private static String joinStrings(String[] args, String delimiter) {
		int length = args.length;
		if(length==0){
			return "";
		}
		StringBuilder sb = new StringBuilder(args[0]);
		for(int i=1;i<length;i++){
			sb.append(delimiter).append(args[i]);
		}
		return sb.toString();
	}
}
