/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.rabbitmq.workqueues;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

/**
 * @author Ravi Kumar
 */
public class Publisher {

    private static final String QUEUE_NAME = "port10";

    public static void main(String[] args) throws IOException, TimeoutException {
        Scanner sc=new Scanner(System.in);
        ConnectionFactory factory = new ConnectionFactory();
        Connection con = factory.newConnection();
        Channel ch = con.createChannel();
        ch.queueDeclare(QUEUE_NAME, true, false, false, null);
        System.out.println("Publishing the meesages is ready now. Please enter your messages to publish");
        System.out.println("To Cancel publish: reply with \"end\"\n");
        while(true){
            String message=sc.nextLine();
            if(message.equalsIgnoreCase("end")){
                break;
            }
            ch.basicPublish("", QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
        }
        ch.close();
        con.close();
    }
}
