package ServerPackage;

import User.User;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Clients is a class that stores all connected clients both offline and online.
 *
 * @author Elliot Lind Palmstadius
 * @version 1.0
 *
 */
public class Clients {
    private ArrayList<User> onlineClients = new ArrayList<>();
    private HashMap<String,User> hashMap;
    private final Server server;

    /**
     * A constructor that loads up clients from file otherwise it creates space.
     * @param server server object so it can communicate with the server.
     */
    public Clients(Server server) {
        this.server = server;
        loadFromFile();
    }
    public synchronized void put(User user){
        if(!hashMap.containsKey(user.getName())){
            //Adding new client
            onlineClients.add(user);
            hashMap.put(user.getName(),user);
        }
        else{
            //Logging in existing user
            hashMap.remove(user.getName());
            hashMap.put(user.getName(),user);
            onlineClients.add(hashMap.get(user.getName()));
            server.loadOldMessage(user.getName());
        }
        saveToFile();
    }
    public synchronized int getConnectedClientHandler(String user) {
        return hashMap.get(user).getConnectedClientHandler();
    }
    public synchronized void setOffline(User user){
        if(onlineClients.contains(user)){
            for(int x = 0; x< onlineClients.size(); x++){
                System.out.println();
                if(onlineClients.get(x).equals(user)){
                    hashMap.get(onlineClients.get(x).getName()).setOnline(false);
                    System.out.println(hashMap.get(onlineClients.get(x).getName()).getName()+" "+hashMap.get(onlineClients.get(x).getName()).isOnline());
                    onlineClients.remove(onlineClients.get(x));
                }
            }
        }
        saveToFile();
    }
    public synchronized User[] getOnlineList(){
        User[] u = new User[onlineClients.size()];
        for(int i = 0; i< onlineClients.size(); i++){
            u[i] = onlineClients.get(i);
        }
        return u;
    }
    public synchronized User getUser(String sentTo){
        return hashMap.get(sentTo);
    }

    public void saveToFile(){
        try {
            File file = new File("server/Clients.dat");
            ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(file)));
            oos.writeObject(onlineClients);
            oos.flush();

        }catch (IOException e) {
            System.out.println("can't write to file");
        }
    }
    public void loadFromFile(){
        try {
            ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream("server/Clients.dat")));
            onlineClients = (ArrayList<User>) ois.readObject();
        }
        catch (ClassNotFoundException | IOException | NullPointerException e) {
            hashMap = new HashMap<>();
            System.out.println("No clients file found");
        }
    }
}
