/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.rabbitmq.exchange;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 *
 * @author Ravi Kumar
 */
public class Subscriber2{

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        Connection con = factory.newConnection();
        Channel ch=con.createChannel();
        ch.exchangeDeclare("logs", BuiltinExchangeType.FANOUT);
        String QUEUE_NAME=ch.queueDeclare().getQueue();
        ch.queueBind(QUEUE_NAME, "logs", "");
        final Consumer consumer=new DefaultConsumer(ch){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message=new String(body);
                System.out.println(message);
            }
        };
        ch.basicConsume(QUEUE_NAME, true, consumer);  
    }
}
