package SmasherServer;
//Contributors:
//Ben Smolin
//Dat Nguyen.
//Mary Kate Durnan
//Robert Stachurski

import java.io.IOException;
import java.net.Socket;
import java.net.ServerSocket;

/**
 * Main server thread of Sudoku Smasher, continually accepts socket connections and
 * adds them to a thread-safe queue for distribution to solver threads
 */
public class ServerMain
{
    public final static int port = 3000;

    public static void main(String[] args) throws InterruptedException, IOException
    {
        SafeQueue buffer = new SafeQueue<Socket>();
        SafeQueue reports = new SafeQueue<int[][]>();
        Solver[] solvers = new Solver[6];
        Logger logger = new Logger(reports);
        ServerSocket server = null;

        try {
            server = new ServerSocket(port);
            for (Solver current : solvers) {
                current = new Solver(buffer, reports);
                current.start();
            }
            logger.start();
            while (true) {
                System.out.println("Waiting for new client...");
                Socket client = server.accept();
                System.out.println("Client accepted...");
                buffer.add(client);
                //client.close();
            }
        }
        finally {
            server.close();
        }

    }

}
