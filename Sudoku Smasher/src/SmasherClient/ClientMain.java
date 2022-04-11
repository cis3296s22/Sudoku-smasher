package SmasherClient;

import SmasherClient.Frame;
public class ClientMain {
    public static void main(String[] args)
    {
        Frame.createAndShowGUI();
        int port = 3000;
        Client client = new Client(port);
    }
}
