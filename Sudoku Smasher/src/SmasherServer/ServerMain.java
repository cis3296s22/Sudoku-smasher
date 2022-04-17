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

    public static void main(String[] args)
    {
       // Server server = new Server(3000);
        final int port = 3000;
        final int MAXTHREADS = 6;
        Solver solvers[] = new Solver[MAXTHREADS];
//        SafeQueue connections = new SafeQueue();
        ServerSocket server = null;


//        for(int i = 0 ; i < MAXTHREADS ; i++)
//        {
//            solvers[i] = new Solver(connections);
//             solvers[i].run();
//            System.out.println("thread " + i + " running");
//
//        }
        try { server = new ServerSocket(port); }
        catch (IOException e) {e.printStackTrace();}




        //server accept loop
        while(true)
        {
            try
            {
                System.out.println("Waiting for new client...");
                Socket client = server.accept();
                System.out.println("Client accepted...");
                Runnable solve = new Solver(client);
                solve.run();
                client.close();
            }
            catch (IOException e){e.printStackTrace();}


        }

        }
//        Runnable a = new Solver(testBoard);
//        Runnable b = new Solver(badTestBoard);
//        a.run();
//        b.run();
//        Debugger.showMatrix(testBoard);

    }

//}
