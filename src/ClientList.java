import java.util.HashMap;

public class ClientList {
    private HashMap<User, Client> map;

    public ClientList(){
         map = new HashMap<User, Client>();
    }

    public synchronized void put(User user, Client client){
        map.put(user, client);
    }

    public synchronized Client get(User user){
        return get(user);
    }


}
