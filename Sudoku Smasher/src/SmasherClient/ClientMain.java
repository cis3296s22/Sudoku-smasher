package SmasherClient;
import music.Player;
import SmasherClient.Frame;
import SmasherServer.Debugger;


public class ClientMain {
    public static void main(String[] args)
    {
        Player.getSound("sound/BGM.wav");
        int[][] testBoard = Debugger.getGoodTest();

        int port = 3000;
        Client client = new Client(port);
        Frame frame = new Frame(client);
        frame.createAndShowGUI();
 //       client.sendPuzzle(testBoard);
        Debugger.showMatrix(client.getPuzzle());
    }
}
