import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class Server extends Thread {
    private ServerSocket serverSocket;
    private ClientList clientlist;

    public Server(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        this.start();
        System.out.println("Server: Server started on port " + port);
        clientlist = new ClientList();
    }

    public void run() {
        while (true) {
            try {
                Socket socket = serverSocket.accept();
                System.out.println("Server: Client connection detected, initiating handler...");

                ClientHandler client = new ClientHandler(socket);

            } catch (IOException e) {
                System.err.println(e);
            }
        }
    }

    private class ClientList {
        private HashMap<User, ClientHandler> map;

        public ClientList(){
            map = new HashMap<User, ClientHandler>();
        }

        public synchronized void put(User user, ClientHandler client){
            map.put(user, client);
        }

        public synchronized ClientHandler get(User user){
            return map.get(user);
        }


    }


    private class ClientHandler extends Thread {
        private Socket socket;
        private ObjectInputStream ois;
        private ObjectOutputStream oos;

        public ClientHandler(Socket socket) throws IOException {
            this.socket = socket;
            System.out.println("Server: Handler and socket established.");
            start();
        }

        public void run() {
            try {
                oos = new ObjectOutputStream(socket.getOutputStream());
                ois = new ObjectInputStream(socket.getInputStream());

                oos.writeObject("/server/get_info");

                // TODO check if user already exists
                User user = (User) ois.readObject();
                clientlist.put(user, this);
                System.out.println("fittan connectade ");

                while (true) {
                    Message message = (Message) ois.readObject();
                    System.out.println("Server: "+ message.getText());
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
            try {
                onDisconnect();
            } catch (Exception e) {
                System.out.println(e);
            }
        }

        public void sendMessage(Message msg){

        }

        public void onDisconnect(){
            try {
                socket.close();
                System.out.println("Socket disconnected");
            }
            catch (IOException e) {
                System.out.println("Error disconnecting socket.");
                System.out.println(e);
            }

        }
    }
}
