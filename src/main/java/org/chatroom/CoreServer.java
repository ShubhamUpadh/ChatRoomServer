package org.chatroom;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Logger;
import java.util.logging.Level;

class CoreServer {
    private final ServerSocket serverSocket;
    private final Logger logger = Logger.getLogger(CoreServer.class.getName());

    public CoreServer(int port) throws IOException {
        this.serverSocket = new ServerSocket(port);
        System.out.println("Listening on port #" + port);
        logger.log(Level.INFO, "Started Server");
    }

    public Socket createSocket(){
        logger.log(Level.INFO, "Created socket");
        try{
            Socket returnSocket = serverSocket.accept();
            logger.log(Level.INFO, "Accepted new client connection from " + returnSocket.getRemoteSocketAddress());
            return returnSocket;
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Not able to create socket ", e);
            throw new RuntimeException(e);
        }
    }

    public String receiveMessage(Socket socket) throws IOException {
        return new DataInputStream(socket.getInputStream()).readUTF();
    }
}
