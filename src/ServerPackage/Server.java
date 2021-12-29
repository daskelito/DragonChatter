package ServerPackage;
import Message.ChatMessage;
import User.User;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;


public class Server {
    private ArrayList<ClientHandler> clientlist;


    public Server(int port) throws IOException {
        clientlist = new ArrayList<>();
        new Connection(port).start();
    }


    private class Connection extends Thread {
        private int port;
        public Connection(int port){
            this.port = port;
        }

        public void run(){
            try(ServerSocket serverSocket = new ServerSocket(port)) {
                while(!Thread.interrupted()){
                    Socket socket = serverSocket.accept();
                    new ClientHandler(socket, clientlist.size());
                }
                System.out.println("Server: Client connection detected, initiating handler...");
            } catch (IOException e) {
                System.err.println(e);
            }
        }
    }

    private class ClientHandler extends Thread {
        private Socket socket;
        private ObjectInputStream ois;
        private ObjectOutputStream oos;
        private int ID;

        public ClientHandler(Socket socket, int ID) throws IOException {
            this.socket = socket;
            this.ID = ID;
            clientlist.add(this);
            start();
            System.out.println("Server: Handler and socket established.");

        }

        public void run() {
            try {
                oos = new ObjectOutputStream(socket.getOutputStream());
                ois = new ObjectInputStream(socket.getInputStream());

                oos.writeObject("/server/get_info");

                // TODO check if user already exists
                User user = (User) ois.readObject();

                System.out.println("Server: client connected");

                while (true) {
                    ChatMessage message = (ChatMessage) ois.readObject();
                    System.out.println("Server: "+ message.getText());
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
            try {
                disconnect();
            } catch (Exception e) {
                System.out.println(e);
            }
        }

        public void sendMessage(ChatMessage msg){

        }

        public void disconnect(){
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
