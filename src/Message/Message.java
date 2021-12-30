package Message;

import java.io.Serializable;
import java.util.Date;

public abstract class Message implements Serializable {
    private String sender;
    private String text;
    private Date timestamp;

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public Date getTimestamp(){
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

}
