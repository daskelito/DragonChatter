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
        for (String s : contactList) {
            clientGUI.addContact(s);
        }
    }

    public void connect(int port) throws IOException {
        new Connection().start();
        System.out.println("Client: connected.");
    }

    public void sendInfoMessage(String receiver, String text){
        System.out.println(currentUser.getImage());
        currentUser.setImage(null);
        InfoMessage infomsg = new InfoMessage(receiver, text, currentUser);
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
                String filename = "ClientFiles/contacts_"+currentUser.getName()+".txt";
                FileWriter fw = new FileWriter(filename, true);
                fw.write( "\n" + name + "\n");
                fw.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Client: Contact already exists: " + name);
        }
    }

    public void readContacts() throws FileNotFoundException {
        String filename = "ClientFiles/contacts_"+currentUser.getName()+".txt";
        Scanner scanner = new Scanner(new File(filename));
        while (scanner.hasNextLine()) {
            String c = scanner.nextLine();
            contactList.add(c);
            clientGUI.addContact(c);
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
                Socket socket = new Socket("localhost", 5252);
                oos = new ObjectOutputStream(socket.getOutputStream());
                ois = new ObjectInputStream(socket.getInputStream());
                sendInfoMessage("server", "online");
                readContacts();
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
                    System.out.print("Client "+currentUser.getName()+": Incoming ");
                    if(message instanceof ChatMessage){
                        System.out.println("ChatMessage");
                        clientGUI.displayMessage((ChatMessage) message);
                    } else if (message instanceof InfoMessage){
                        System.out.println("InfoMessage");
                        System.out.println(message.getText());
                        String[] m = message.getText().split(":");
                        if(m[0].equals("online")){
                            System.out.println("AddOnline");
                            System.out.println(m[1]);
                            clientGUI.addOnline(m[1]);
                        }
                        else if(m[0].equals("offline")){
                            clientGUI.removeOnline(m[1]);
                        }
                    }

                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

}
