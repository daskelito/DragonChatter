package Message;

import java.io.Serializable;

public class InfoMessage extends Message implements Serializable {
    private String receiver;

    public InfoMessage(String receiver, String text){
        setText(text);
        this.receiver = receiver;
    }


}
