package ClientPackage;
import Message.Message;
import Message.ChatMessage;
import Message.InfoMessage;
import User.User;
import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Client extends Thread {
    private Socket socket;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    private User currentUser;
    private ArrayList<String> contactList;
    private ClientGUI clientGUI;

    public Client(int port) throws IOException {
        contactList = new ArrayList<>();
        clientGUI = new ClientGUI(this);
        readContacts("contacts.txt");
        for (String s : contactList) {
            clientGUI.addContact(s);
        }
    }

    public void connect(int port) throws IOException {
        new Connection().start();
        System.out.println("Client: connected.");
    }

    public void sendInfoMessage(String receiver, String text){
        InfoMessage infomsg = new InfoMessage(receiver, text);
        try {
            oos.writeObject(infomsg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendChatMessage(ArrayList<String> receivers, String message) {
        Message messageToSend = new ChatMessage(currentUser.getName(), receivers, message);
        try {
            oos.writeObject(messageToSend);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendPicMessage(ArrayList<String> receivers, String message, ImageIcon picture) {
        Message messageToSend = new ChatMessage(currentUser.getName(), receivers, message, picture);
        try {
            oos.writeObject(messageToSend);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String> getContactList() {
        return contactList;
    }

    public void addContact(String name) {
        if (!contactList.contains(name)) {
            contactList.add(name);
            try {
                FileWriter fw = new FileWriter("contacts.txt", true);
                fw.write(name + "\n");
                fw.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Client: Contact already exists: " + name);
        }
    }

    public void readContacts(String path) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(path));
        while (scanner.hasNextLine()) {
            contactList.add(scanner.nextLine());
        }
    }

    public void setCurrentUser(User user) {
        currentUser = user;
        System.out.println("Client: User set to " + currentUser.getName());
    }

    public User getCurrentUser() {
        return currentUser;
    }


    private class Connection extends Thread {
        public void run() {
            try {
                Socket socket = new Socket("localhost", 888);
                oos = new ObjectOutputStream(socket.getOutputStream());
                ois = new ObjectInputStream(socket.getInputStream());
                sendInfoMessage("server", "online");
                new Listener().start();
                while (!Thread.interrupted()) {
                    //to keep connection alive
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    private class Listener extends Thread {
        public void run() {
            try {
                while (true) {
                    Message message = (Message) ois.readObject();
                    if(message instanceof ChatMessage){
                        clientGUI.displayMessage((ChatMessage) message);
                    } else if (message instanceof InfoMessage){

                    }

                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

}
