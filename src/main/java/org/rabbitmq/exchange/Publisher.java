/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.rabbitmq.exchange;

import com.rabbitmq.client.BuiltinExchangeType;
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
public class Publisher {

    public static void main(String[] args) throws IOException,TimeoutException {
        Scanner sc=new Scanner(System.in);
        ConnectionFactory factory = new ConnectionFactory();
        Connection con = factory.newConnection();
        Channel ch = con.createChannel();
        ch.exchangeDeclare("logs", BuiltinExchangeType.FANOUT);
          while (true) {
              String  message=sc.nextLine();
              if(message.equalsIgnoreCase("end"))
                  break;
             ch.basicPublish("logs","", null, message.getBytes());  
        }
        ch.close();
        con.close();
    }
}
