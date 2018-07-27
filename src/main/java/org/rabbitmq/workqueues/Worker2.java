/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.rabbitmq.workqueues;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author Ravi Kumar
 */
public class Worker2 {

    private static final String QUEUE_NAME = "port10";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        Connection con = factory.newConnection();
        final Channel ch = con.createChannel();
        ch.basicQos(1);
        ch.queueDeclare(QUEUE_NAME, true, false, false, null);
        System.out.println("Worker 2 started");
        final Consumer consumer = new DefaultConsumer(ch) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message=new String(body);
                System.out.println(message);
                try{
                    doSomething(message);
                }finally{
                    System.out.println("Work Done.Worker 2 is free.");
                    //to acknowledge to the broker that the work has done
                    ch.basicAck(envelope.getDeliveryTag(), false);
                }
            }
            private void doSomething(String message) {
                for(Character c:message.toCharArray()){
                    try{
                    Thread.sleep(1000);
                    }catch(InterruptedException ie){
                        System.out.println("Work has been stopped unexpectedly. ReDO the work");
                    }
                }
            }
        };
        //Here false means Manual Acknowledgement (We can able send ack)Turned ON, IF true it will OFF
        ch.basicConsume(QUEUE_NAME, false, consumer);

    }
}
