import javax.swing.*;
import java.io.Serializable;

public class User implements Serializable {
    private String name;
    private ImageIcon imgage;

    public User(String name, ImageIcon imgage){
        this.name = name;
        this.imgage = imgage;
    }

    public String getName(){
        return name;
    }

    public ImageIcon getImage(){
        return imgage;
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
}

