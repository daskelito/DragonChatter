package ServerPackage;

import Message.ChatMessage;
import Message.InfoMessage;
import Message.Message;
import User.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

/**
 * Server is the main class for the server side activity of the chat system.
 *
 * @author Elliot Lind Palmstadius
 * @version 1.0
 *
 */
public class Server {
    private final ArrayList<ClientHandler> list = new ArrayList<ClientHandler>();
    private final Clients clients;
    private final ServerGui LoggerGui;
    private final Logger Logger;
    public Server(int port){
        clients = new Clients(this);
        LoggerGui = new ServerGui(this);
        Logger = new Logger(this);
        new Connection(port).start();
    }
    /**
     * This method loads up messages from the Logger when a existing user Loggers in.
     * @param username name of the user that is Loggerging in.
     */
    public synchronized void loadOldMessage(String username) {
        ArrayList<Message> oldMessages = Logger.getMessageList(username);
        int clientHandler = clients.getConnectedClientHandler(username);
        for (Message oldMessage : oldMessages){
            list.get(clientHandler).SendToClient(clientHandler, (ChatMessage) oldMessage);
        }
    }
    public synchronized void setLog(String[] tempArray) {
        LoggerGui.setList(tempArray);
    }
    /*
    public synchronized void filter(Timestamp first, Timestamp last){
        LoggerGui.setList(Logger.filter(first,last));
    }
    /**
     * Sets up a sustainable connection
     */
    private class Connection extends Thread{
        private int port;

        public Connection(int port) {
            this.port = port;
        }

        public void run() {
            try (ServerSocket serverSocket = new ServerSocket(port)) {
                while (!Thread.interrupted()) {
                    Socket socket = serverSocket.accept();
                    new ClientHandler(socket, list.size());
                    System.out.println("Server: client with ID " + list.size() + " added.");
                }
            } catch (IOException e) {
                System.err.println(e);
            }
        }
    }
    /**
     * ClientHandler is a private class for each client that connects to the server.
     */
    private class ClientHandler extends Thread {
        private ObjectInputStream ois;
        private ObjectOutputStream oos;
        private int clientHandlerID = 0;
        private User user;
        public ClientHandler(Socket s, int ID) {
            list.add(this);
            clientHandlerID = ID;
            try {
                oos = new ObjectOutputStream(s.getOutputStream());
                ois = new ObjectInputStream(s.getInputStream());
                start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        @Override
        public void run() {
            try {
                while (!Thread.interrupted()) {
                    //Incomming message.
                    Message m = (Message) ois.readObject();
                    m.setTimestamp(new Date(System.currentTimeMillis()));
                    //Determine what type of message
                    if(m instanceof ChatMessage){
                        ChatMessage message = (ChatMessage) m;

                        for( String u : message.getReceivers()) {
                            if (clients.getUser(u).isOnline()) {

                                SendToClient(clients.getConnectedClientHandler(u), message);
                            }
                            LoggerGui.setList(Logger.addToLog(m));
                        }
                    }
                    else if(m instanceof InfoMessage){
                        InfoMessage message = (InfoMessage) m;
                        //Loging in
                        InfoMessage update = null;
                        if(message.getText().equals("online")){
                            user = new User(message.getUser().getName(),message.getUser().getImage());
                            user.setOnline(true);
                            user.setConnectedClientHandler(clientHandlerID);
                            clients.put(user);
                            for(User u : clients.getOnlineList()){
                                update = new InfoMessage("online:"+u.getName()+":");
                                SendToAll(update);
                                LoggerGui.setList(Logger.addToLog(update));
                            }
                        }
                        //Logging off
                        else if(message.getText().equals("offline")) {
                            clients.setOffline(message.getUser());
                            list.remove(this);
                            update = new InfoMessage("offline:"+user.getName());
                            LoggerGui.setList(Logger.addToLog(update));
                            SendToAll(update);
                            disconnect();
                            break;
                        }
                    }
                }
                try {
                    oos.close();
                    ois.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        private void disconnect() {
            try {
                oos.flush();
                oos.close();
                ois.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        private void SendToAll(InfoMessage str) {
            for(ClientHandler c : list){
                if(clients.getUser(c.user.getName()).isOnline()){
                    c.write(str);
                }
            }
        }
        private void SendToClient(int i, ChatMessage message) {
            list.get(i).write(message);
        }
        /**
         * writing to the client side of this clienthandler object
         * @param message message
         */
        public void write(Message message) {
            try {
                oos.writeObject(message);
                oos.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        Server s = new Server(3232);
    }
}
