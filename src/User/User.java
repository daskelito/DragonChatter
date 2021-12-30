package User;

import javax.swing.*;
import java.io.Serializable;

public class User implements Serializable {
    private String name;
    private ImageIcon image;
    private boolean online;
    private int connectedClientHandler;


    public User(String name, ImageIcon image){
        this.name = name;
        this.image = image;
        online = false;
    }

    public String getName(){
        return name;
    }

    public ImageIcon getImage(){
        return image;
    }

    public int hashCode(){
        return name.hashCode();
    }

    public boolean equals(Object obj){
        if(obj!=null && obj instanceof User){
            return name.equals(((User)obj).getName());
        }
        return false;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    public int getConnectedClientHandler() {
        return connectedClientHandler;
    }

    public boolean isOnline(){
        return online;
    }

    public void setConnectedClientHandler(int clientHandlerID) {
        this.connectedClientHandler = clientHandlerID;
    }

    public void setImage(ImageIcon image) {
        this.image = image;
    }
}

