package org.chatroom;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {

        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the port number");
        int port = sc.nextInt();

        CoreServer coreServer = new CoreServer(port);
        Socket socket = coreServer.createSocket();
    }
}