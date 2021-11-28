import javax.swing.*;
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
        gatherInfo();
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
        start();
        System.out.println("Client: Client connected.");
    }

    public void run(){

        try {
            socket = new Socket("localhost", 888);
            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());

            new Listener(ois);

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
                oos.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private class Listener extends Thread {
        private Socket socket;
        private ObjectInputStream ois;

        private Listener(ObjectInputStream ois) throws IOException  {
            this.ois = ois;
            start();
        }

        public void run() {
            try {
                //ois = new ObjectInputStream(socket.getInputStream());
                while (true) {
                    String request = (String) ois.readObject();
                    if (request.startsWith("/server")){
                        String cmd = request.split("/")[2];
                        System.out.println("incoming command: " + cmd);

                        if (cmd.equals("get_info")){
                            System.out.println("IbnasdksdNFO");
                            oos.writeObject(currentUser);
                        }

                        // do stuff with request
                        //if (request.equals(""))
                    }
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }


    public void printContacts(){

    }

}
