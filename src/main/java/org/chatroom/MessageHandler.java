package org.chatroom;

import java.io.*;
import java.net.Socket;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

class MessageHandler {
    private final Socket socket;
    private final String userName;
    private PrintWriter out;
    private BufferedReader in;
    private Logger logger = Logger.getLogger(MessageHandler.class.getName());
    private static RoomHandler roomHandler = new RoomHandler();
    private String currRoomName;

    public MessageHandler(Socket socket, String userName){
        this.socket = socket;
        this.userName = userName;
        try{
            this.out = new PrintWriter(
                    new OutputStreamWriter(socket.getOutputStream()), true
            );
            logger.log(Level.INFO,"Created output stream");

            this.in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream())
            );
            logger.log(Level.INFO,"Created input stream");
        }
        catch (IOException e){
            logger.log(Level.SEVERE, "Not able to create OutputStream ", e);
        }
    }

    public void listenMessage(){
        while (!socket.isClosed()){
            // I'm thinking of listening all the messages over here
            // parse the message, a connected user is being handled over here, hence
            try {
                String message = in.readLine();

                if (message == null){
                    handleUserLogoff();
                    return;
                }

                if (!isValidMessage(message)){
                    out.println("Please join a room !!");
                    continue;
                }
                parseMessage(message);

            } catch (IOException e) {
                logger.log(Level.SEVERE, "e");
            }
        }
    }

    private void parseMessage(String message) {
        // possible commands - /join (if room is null), /leave (if room not null), /options (available options)
        // /list - list of all users in the room
        // /exit - end session
        // without any / -> send this messsage to all
        // *username of another user* -> send dm to another user

        List<String> messageSplit = List.of(message.split(" "));
        if (messageSplit.isEmpty()) {
            out.println("Received empty message !!!");
        }
        else if (messageSplit.getFirst().charAt(0) == '/'
                && !isAValidCommand(messageSplit.getFirst().toLowerCase())){
            out.println("Not a valid command");
            validCommands();
        }
        else{
            parseCommand(messageSplit);
        }
        return;
    }

    private void validCommands() {
        out.print("These are the options :: ");
        if (currRoomName == null) out.print("/join, ");
        if (currRoomName != null) out.print("/leave, ");
        out.println("/options, /list, /exit ");
    }

    private void parseCommand(List<String> messageSplit) {
        if (messageSplit.getFirst().equalsIgnoreCase("/options")){
            validCommands();
        }
        else if (messageSplit.getFirst().equalsIgnoreCase("/join")){
            if (messageSplit.size() != 2){
                out.println("Not a valid format \n/join <RoomName>");
                validCommands();
            }
            currRoomName = roomHandler.joinRoom(messageSplit.get(1), socket, userName);
        } // leave, list, exit
        else if (messageSplit.getFirst().equalsIgnoreCase("/leave")){
            if (messageSplit.size() != 1 || currRoomName == null){
                out.println("Not a valid format \n/leave");
                validCommands();
            }
            roomHandler.leaveRoom(userName, currRoomName);
        }

        else if (messageSplit.getFirst().equalsIgnoreCase("/list")){
            if (messageSplit.size() != 1 || currRoomName == null){
                out.println("Not a valid format \n/list");
                validCommands();
            }
            roomHandler.listUsers(currRoomName);
        }

        else if (messageSplit.getFirst().equalsIgnoreCase("/exit")){
            if (messageSplit.size() != 1 || currRoomName == null){
                out.println("Invalid command ");
                validCommands();
            }
            roomHandler.leaveRoom();
        }
    }

    private boolean isAValidCommand(String command) {
        return command.equals("/join") || command.equals("/leave")
                || command.equals("/options") || command.equals("/list")
                || command.equals("/exit") || command.equals("/help");
    }

    private boolean isValidMessage(String message) {
        return currRoomName != null;
    }

    private void handleUserLogoff(){

        if (currRoomName != null){
            roomHandler.leaveRoom(currRoomName);
            logger.log(Level.INFO, "Removed " + userName + " from the room " + currRoomName);
        }

        UserName.deleteUser(userName);
        logger.log(Level.INFO,"Deleted the user");

        logger.log(Level.INFO,"Trying to close the connection");
        try {
            socket.close();
            logger.log(Level.INFO,"Socket Connection Closed");
        } catch (IOException e) {
            logger.log(Level.SEVERE,"Error closing the socket",e);
        }

    }
}
