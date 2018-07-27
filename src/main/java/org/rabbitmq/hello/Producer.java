/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.rabbitmq.hello;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

/**
 *
 * @author Ravi Kumar
 */
public class Producer {

    private static final String QUEUE_NAME = "port1";

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        Scanner sc = new Scanner(System.in);
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setPort(5672);
        Connection con = factory.newConnection();
        Channel channel = con.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        while (true) {
              String  message=sc.nextLine();
              if(message.equalsIgnoreCase("end"))
                  break;
             channel.basicPublish("",QUEUE_NAME, null, message.getBytes());  
        }
        channel.close();
        con.close();
    }
}
