package Main;

import ClientPackage.Client;
import ServerPackage.Server;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {

        Server server = new Server(5252);
        Client client1 = new Client(5252);
        Client client2 = new Client(5252);

    }

}
