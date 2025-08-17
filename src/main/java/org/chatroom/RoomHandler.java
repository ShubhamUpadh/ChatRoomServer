package org.chatroom;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RoomHandler {
    private ConcurrentHashMap<String, Room> rooms = new ConcurrentHashMap<>();
    private PrintWriter out;
    private BufferedReader in;
    private Logger logger = Logger.getLogger(RoomHandler.class.getName());

    public RoomHandler(){
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

    public String  joinRoom(String roomName, Socket socket, String userName){
        if (rooms.containsKey(roomName)){
            Room room = rooms.get(roomName);
            room.addMember(userName, socket);
            // add functionality for returning message to user that they have been added
            return null;
        }

        Room room = new Room(roomName, userName, socket); // new room created
        rooms.put(roomName, room);
        return roomName;
    }

    public void leaveRoom(String userName, String currRoomName){
        // if user not in room, return message
        // else user will be removed from the map
        if (!rooms.containsKey(currRoomName)){
//            out.println("Not a valid format \n/join <RoomName>");
            return;
        }
        Room room = rooms.get(currRoomName);
        room.removeMember(userName);
        if (room.listAllMembers().isEmpty()){
            rooms.remove(userName);
        }
    }

    public void listUsers(String currRoomName) {
        if (!rooms.containsKey(currRoomName)){
//            out.println("No room exists by the name " + currRoomName);
            return;
        }
        Room room = rooms.get(currRoomName);
        room.listAllMembers();
    }

    public void messageRoom(String userName, String currRoomName, String message) {
        if (!rooms.containsKey(currRoomName)){
            out.println("No room exists by the name " + currRoomName);
            return;
        }
        Room room = rooms.get(currRoomName);
        if (!room.doesMemberExist(userName)){
            out.println("User : " + userName + "  not present in the roomname : " + currRoomName);
            return;
        }
        if (room.listAllMembers().size() == 1){
            out.println("No one present in the room");
            return;
        }
        room.messageOthers(userName);
    }
}
