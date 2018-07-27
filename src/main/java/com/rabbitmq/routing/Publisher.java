/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rabbitmq.routing;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;


/**
 * @author Ravi Kumar
 */
public class Publisher {

    public static void main(String[] args) throws IOException, TimeoutException {
        Scanner sc = new Scanner(System.in);
        String route;
        String message;
        ConnectionFactory factory = new ConnectionFactory();
        Connection con = factory.newConnection();
        Channel ch = con.createChannel();
        ch.exchangeDeclare("send", BuiltinExchangeType.DIRECT);
        while (true) {
            System.out.println("To which route you want to log now!!  1? or 2?");
            route = sc.nextLine();
            if (route.equalsIgnoreCase("1")) {
                System.out.println("Enter log:");
                message = sc.nextLine();
                if(message.equalsIgnoreCase("end"))
                    break;
                ch.basicPublish("send", route, null, message.getBytes());
            } else if (route.equalsIgnoreCase("2")) {
                System.out.println("Enter log:");
                message = sc.nextLine();
                if(message.equalsIgnoreCase("end"))
                    break;
                ch.basicPublish("send", route, null, message.getBytes());
            } else {
                System.out.println("Invalid selection.. ");
            }
        }
        ch.close();
        con.close();
    }
}
