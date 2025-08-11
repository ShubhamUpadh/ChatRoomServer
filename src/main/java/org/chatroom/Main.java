package org.chatroom;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    Logger logger = Logger.getLogger(Main.class.getName());
    public static void main(String[] args) throws IOException {

        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the port number");
        int port = sc.nextInt();

        CoreServer coreServer = new CoreServer(port);

        while (true) { // is this a good multithreading way
            Socket socket = coreServer.createSocket();
            new Thread(() -> {
                UserNameHandler handler = new UserNameHandler(socket);
                handler.setUserName();
                // now start processing the user messages
            }).start();
        }
    }
}