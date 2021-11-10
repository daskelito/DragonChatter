import javax.swing.*;

public class User {
    private String name;
    private ImageIcon img;

    public User(String name, ImageIcon img){
        this.name = name;
        this.img = img;
    }

    public String getName(){
        return name;
    }

    public ImageIcon getImage(){
        return img;
    }
}

