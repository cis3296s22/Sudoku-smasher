package SmasherClient;
import music.Player;
import SmasherClient.Frame;
import SmasherServer.Debugger;

import javax.swing.*;

/**
 * Main client process, creates a socket connection then a GUI
 * allows for a single solve attempt currently
 */
public class ClientMain {
    public static void main(String[] args)
    {
//        Player.getSound("sound/BGM.wav");
   //     int[][] testBoard = Debugger.getBadTest();

        int port = 3000;
        Client client = new Client(port);
        Frame frame = new Frame(client);
        frame.createAndShowGUI();
//        int[][] received_board = client.getPuzzle();
 //       client.sendPuzzle(testBoard);
    }
}
