package Message;

import java.io.Serializable;

public class ServerMessage extends Message implements Serializable {
    private String receiver;

    public ServerMessage(String receiver, String text){
        setText(text);
        this.receiver = receiver;
    }
}
