import javax.swing.*;
import java.io.*;
import java.lang.reflect.Executable;
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
        connect(port);
        contactList = new ArrayList<>();
        clientGUI = new ClientGUI(this);
        readContacts("contacts.txt");
        for (String s : contactList) {
            clientGUI.addContact(s);
        }
    }


    public void connect(int port) throws IOException {
        start();
        System.out.println("Client: Client connected.");
    }

    public void login() {

    }

    public ArrayList<String> getContactList() {
        return contactList;
    }

    public void addContact(String name) {
        if(!contactList.contains(name)) {
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
        System.out.println("Client: User set to " + currentUser.getName() + " with picture " + currentUser.getImage());
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void run() {
        try {
            socket = new Socket("localhost", 888);
            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());

            new Listener(ois);

        } catch (IOException e) {
            e.printStackTrace();
        }

        while (true) {

            Message msg = new Message("arne", new ArrayList<String>(), "hi");

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

        private Listener(ObjectInputStream ois) throws IOException {
            this.ois = ois;
            start();
        }

        public void run() {
            try {
                //ois = new ObjectInputStream(socket.getInputStream());
                while (true) {
                    String request = (String) ois.readObject();
                    if (request.startsWith("/server")) {
                        String cmd = request.split("/")[2];
                        System.out.println("incoming command: " + cmd);

                        if (cmd.equals("get_info")) {
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


}
