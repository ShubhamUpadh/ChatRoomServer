package org.chatroom;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserName {
    private static Set<String> usernames = ConcurrentHashMap.newKeySet();
    private static final Logger logger = Logger.getLogger(UserName.class.getName());

    public static boolean doesUserExist(String name) {
        logger.log(Level.INFO, "Checking if user " + name + " exists or not");
        return usernames.contains(name);
    }

    public static void addUser(String name){
        logger.log(Level.INFO, "Adding user " + name);
        usernames.add(name);
    }

    public static void deleteUser(String name){
        logger.log(Level.INFO,"Removed user " + name);
        usernames.remove(name);
    }

}
