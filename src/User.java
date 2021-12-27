import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

public class User implements Serializable {
    private String name;
    private ImageIcon image;


    public User(String name, ImageIcon image){
        this.name = name;
        this.image = image;
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


}

