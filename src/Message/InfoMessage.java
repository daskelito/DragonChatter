package Message;

import User.User;

import java.io.Serializable;

public class InfoMessage extends Message implements Serializable {
    private String receiver;
    private User user;

    public InfoMessage(String receiver, String text){
        setText(text);
        this.receiver = receiver;
    }
    public InfoMessage( String text){
        setText(text);
    }

    public InfoMessage(String receiver, String text, User user){
        setText(text);
        this.receiver = receiver;
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
