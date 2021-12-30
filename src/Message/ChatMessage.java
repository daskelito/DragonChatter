package Message;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class ChatMessage extends Message implements Serializable {
    private ImageIcon img;
    private String sender;
    private ArrayList<String> receivers;

    public ChatMessage(String sender, ArrayList<String> receivers, String text, ImageIcon img) {
        this.sender = sender;
        this.receivers = receivers;
        this.img = img;
        setText(text);
    }

    public ChatMessage(String sender, ArrayList<String> receivers, String text) {
        this.sender = sender;
        this.receivers = receivers;
        setText(text);
    }

    public String getSender(){
        return sender;
    }

    public void setImg(ImageIcon img) {
        this.img = img;
    }

    public ImageIcon getImg() {
        return img;
    }

    public ArrayList<String> getReceivers() {
        return receivers;
    }
}
