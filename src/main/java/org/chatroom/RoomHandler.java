package org.chatroom;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RoomHandler {
    private ConcurrentHashMap<String, Room> rooms = new ConcurrentHashMap<>();
    private Logger logger = Logger.getLogger(RoomHandler.class.getName());

    public RoomHandler(){
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

    public List<String> listUsers(String currRoomName) {
        logger.log(Level.INFO, "Invoking listUsers");
        if (!rooms.containsKey(currRoomName)){
//            out.println("No room exists by the name " + currRoomName);
            return new ArrayList<>();
        }
        Room room = rooms.get(currRoomName);
        return room.listAllMembers();
    }

    public void messageRoom(String userName, String currRoomName, String message) {

        Room room = rooms.get(currRoomName);

        if (room.listAllMembers().size() == 1){
            room.messageSelf(userName);
            return;
        }
        room.messageOthers(userName, message);
    }
}
