package SmasherServer;
//Contributors:
//Ben Smolin
//Dat Nguyen.
//Mary Kate Durnan
//Robert Stachurski

import java.io.IOException;
import java.net.Socket;
import java.net.ServerSocket;

public class ServerMain
{
    public final static int port = 3000;

    public static void main(String[] args) throws InterruptedException, IOException
    {
        SafeQueue buffer = new SafeQueue();
        Solver[] solvers = new Solver[6];
        ServerSocket server = null;

        try {
            server = new ServerSocket(port);
            for (Solver current : solvers) {
                current = new Solver(buffer);
                current.start();
            }
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
