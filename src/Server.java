import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread {
    private ServerSocket serverSocket;

    public Server(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        this.start();
        System.out.println("Server started on port " + port);
    }

    public void run() {
        while (true) {
            try {
                Socket socket = serverSocket.accept();
                System.out.println("Client connection detected, initiating handler...");
                new ClientHandler(socket);
            } catch (IOException e) {
                System.err.println(e);
            }
        }
    }

    private class ClientHandler extends Thread {
        private Socket socket;
        private ObjectInputStream ois;
        private ObjectOutputStream oos;

        public ClientHandler(Socket socket) throws IOException {
            this.socket = socket;
            System.out.println("Handler and socket established.");
            ois = new ObjectInputStream(socket.getInputStream());
            oos = new ObjectOutputStream(socket.getOutputStream());
            start();
        }

        public void run() {
            try {
                while (!interrupted()) {
                    Message message = (Message) ois.readObject();
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
            try {
                socket.close();
                System.out.println("Socket closed.");
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }
}
