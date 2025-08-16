package org.chatroom;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    private static Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) throws IOException {

        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the port number :: ");
        int port = sc.nextInt();

        CoreServer coreServer = new CoreServer(port);

        while (true) { // is this a good multithreading way
            logger.log(Level.INFO, "Creating a socket");
            Socket socket = coreServer.createSocket();
            new Thread(() -> {
                logger.log(Level.INFO,"Trying to set a username");
                UserNameHandler userNameHandler = new UserNameHandler(socket);
                String userName = userNameHandler.setUserName();
                // now start processing the user messages
                logger.log(Level.INFO, "Calling messageHandler constructor");
                MessageHandler messageHandler = new MessageHandler(socket, userName);

                logger.log(Level.INFO,"Calling listenMessage");
                messageHandler.listenMessage();
            }).start();
        }
    }
}