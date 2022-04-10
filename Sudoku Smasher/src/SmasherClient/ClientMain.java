package SmasherClient;

import SmasherClient.Frame;
public class ClientMain {
    public static void main(String[] args)
    {
        Frame.createAndShowGUI();
        Client client = new Client();
    }
}
