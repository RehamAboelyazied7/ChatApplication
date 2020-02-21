package com.iti.chat.util;

import com.iti.chat.model.User;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Session {

    private User user;
    private static Session instance;

    private Session() {

    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public static Session getInstance() {
        if (instance == null) {
            instance = new Session();
        }
        return instance;
    }

    public static void signOutInstance() {

        instance.user = null;
        instance = null;
        
        try ( PrintWriter writer = new PrintWriter("userAuthenticationInfo.txt")) {
            writer.print("");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Session.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
