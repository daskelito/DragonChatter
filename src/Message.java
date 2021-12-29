import javax.swing.*;
import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Message implements Serializable {
    String text;
    ImageIcon img;
    Date timestamp;
    String sender;
    ArrayList<String> receivers;

    public Message(String sender, ArrayList<String> receivers, String text, ImageIcon img) {
        this.sender = sender;
        this.receivers = receivers;
        this.text = text;
        this.img = img;
        this.timestamp = new Date(System.currentTimeMillis());
    }

    public Message(String sender, ArrayList<String> receivers,String text) {
        this.sender = sender;
        this.receivers = receivers;
        this.text = text;
        this.timestamp = new Date(System.currentTimeMillis());
    }

    public Message(String sender, ArrayList<String> receivers,ImageIcon img) {
        this.sender = sender;
        this.receivers = receivers;
        this.img = img;
        this.timestamp = new Date(System.currentTimeMillis());
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setImg(ImageIcon img) {
        this.img = img;
    }

    public ImageIcon getImg() {
        return img;
    }

    public Date getTimestamp(){
        return timestamp;
    }
}
