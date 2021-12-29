package Main;

import ClientPackage.Client;
import ServerPackage.Server;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {

        Server server = new Server(888);
        Client client1 = new Client(888);


    }

}
