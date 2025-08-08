package org.chatroom;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Logger;
import java.util.logging.Level;

class CoreServer {
    private int port;
    private final ServerSocket serverSocket = new ServerSocket(port);
    private final Logger logger = Logger.getLogger(CoreServer.class.getName());

    public CoreServer(int port) throws IOException {
        this.port = port;
        System.out.println("Listening on port #" + port);
        logger.log(Level.INFO, "Started Server");
    }

    public Socket createSocket() throws IOException {
        logger.log(Level.INFO, "Created socket");
        return serverSocket.accept();
    }

    public String receiveMessage(Socket socket) throws IOException {
        return new DataInputStream(socket.getInputStream()).readUTF();
    }
}
