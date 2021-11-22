import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Client extends Thread{
    private Socket socket;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    private User currentUser;
    private Scanner scanner;


    public Client(int port) throws IOException {
        scanner = new Scanner(System.in);
        //gatherInfo();
        connect(port);
    }

    public void gatherInfo(){
        System.out.println("Enter name.");
        String name = scanner.nextLine();
        ImageIcon image = new ImageIcon("G:\\Dokument\\Uni2020\\Pågående\\OPTOD\\Data\\Images\\carrot.png");
        currentUser = new User(name, image);
        System.out.println("User created with name '" + name + "' and image 'carrot.png'");
    }

    public void connect(int port) throws IOException {
        socket = new Socket("localhost", port);
        start();
        System.out.println("Client: Client connected.");
    }

    public void run(){
        try {
            ois = new ObjectInputStream(socket.getInputStream());
            oos = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        while(true){
            System.out.println("Client: enter a message");
            String text = scanner.nextLine();
            Message msg = new Message(text);
            System.out.println("Client: Message Entered");
            try {
                oos.writeObject(msg);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void printContacts(){

    }

}
