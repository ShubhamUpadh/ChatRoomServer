package org.chatroom;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Room {
    private final String roomName;
    private final String createdBy;
    private ConcurrentHashMap<String, Socket> memberSocketMap = new ConcurrentHashMap<>();
    Logger logger = Logger.getLogger(Room.class.getName());

    public Room(String roomName, String createdBy, Socket socket){
        this.roomName = roomName;
        this.createdBy = createdBy;
        this.memberSocketMap.put(createdBy, socket);
        memberSocketMap.put(createdBy, socket);
    }
// add, remove, doesUserExist, listAll
    private void addMember(String name, Socket socket){
        logger.log(Level.INFO, "Adding member " + name);
        memberSocketMap.put(name, socket);
    }

    public String getRoomName(){
        return this.roomName;
    }

    public void removeMember(String name){
        logger.log(Level.INFO, "Removing member " + name);
        memberSocketMap.remove(name); // the socket shouldn't get deleted
    }

    public boolean doesMemberExist(String name){
        return memberSocketMap.containsKey(name);
    }

    public List<String> listAllMembers(){
        return memberSocketMap.keySet().stream().toList();
    }


    public String getCreatedBy() {
        return createdBy;
    }
}
