package org.chatroom;

import java.io.*;
import java.net.Socket;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserNameHandler {
    private final Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private final Logger logger = Logger.getLogger(UserNameHandler.class.getName());

    public UserNameHandler(Socket socket){
        this.socket = socket;
        try {
            this.out = new PrintWriter(
                    new OutputStreamWriter(socket.getOutputStream()), true
            );
            logger.log(Level.INFO,"Created output stream");
            this.in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream())
            );
            logger.log(Level.INFO,"Created Input stream");
        }
        catch (IOException e){
            logger.log(Level.WARNING, "Error creating input/output stream" + Arrays.toString(e.getStackTrace()));
        }
    }

    public String setUserName(){
        // ask the user to create a userName
        while (!socket.isClosed()){
            String name = ableToSetUsername();
            if (name != null){
                logger.log(Level.INFO,"Username will be set");
                return name;
            }
        }
    }

    private String ableToSetUsername(){

        try {
            out.println("Set a username (< 9 characters)");
            String name = in.readLine();

            if (name == null){
                logger.log(Level.WARNING,"Client disconnect while setting username");
                return null;
            }

            if (name.length() > 8) {
                out.println("Please enter a username of less than 9 characters");
                return null;
            }

            if (UserName.doesUserExist(name)){
                out.println("Username already exists " + name);
                return null;
            }
            else {
                out.println("Welcome to the server " + name);
                UserName.addUser(name);
                return name;
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Not able to read username" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }

}
