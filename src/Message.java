import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Date;

public class Message {
    String text;
    ImageIcon img;
    Date timestamp;
    User sender;
    ArrayList<User> receivers;

    public Message(User sender, ArrayList<User> receivers, String text, ImageIcon img) {
        this.sender = sender;
        this.receivers = receivers;
        this.text = text;
        this.img = img;
        this.timestamp = new Date(System.currentTimeMillis());
    }

    public Message(String text) {
        this.text = text;
    }

    public Message(ImageIcon img){
        this.img = img;
    }

    public void setText(String text){
        this.text = text;
    }

    public String getText(){
        return text;
    }

    public void setImg(ImageIcon img){
        this.img = img;
    }

    public ImageIcon getImg(){
        return img;
    }
}
