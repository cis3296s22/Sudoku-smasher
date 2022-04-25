package SmasherClient;
import music.Music;
import music.Player;
import SmasherClient.Frame;
import SmasherServer.Debugger;

import javax.swing.*;


public class ClientMain {
    public static void main(String[] args)
    {
        //Music.setup();
        int port = 3000;
        Client client = new Client(port);
        Frame frame = new Frame(client);
        frame.createAndShowGUI();
    }
}
