package SmasherServer;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private Socket socket = null;
    private ServerSocket server = null;
    private ObjectInputStream in = null;
    private ObjectOutputStream out = null;
    private int listeningPort = 0;

    public void handleConnection(Socket socket) {

    }

    // Call this method to get the current thread to start listening on the port
    public void listen(int port) {

    }

    public Server(int port) {
        System.out.println("Server started on port");
        this.listeningPort = port;
        try{
            server = new ServerSocket(port);
            System.out.println("Server started");
 
            System.out.println("Waiting for a client ...");

            // Server blocks waiting for a new connection from client
            socket = server.accept();
            System.out.println("Client accepted");
            Runnable solve = new Solver(socket);
            solve.run();
            socket.close();
            // takes input from the client socket
  //          in = new ObjectInputStream(socket.getInputStream());
   //         out = new ObjectOutputStream(socket.getOutputStream());

            System.out.println(in);

//            System.out.println("before thread");
//            Runnable test = new Solver(socket);
//            test.run();
//            System.out.println("after thread");

         //   String line = "";
 
            // reads message from client until "Over" is sent
           // while (!line.equals("Over"))
           // {
//                try
//                {
//                    int[][] board;
//                    board= (int[][]) in.readObject();
//                    Debugger.showMatrix(board);
//                    Runnable solve = new Solver(board);
//                    solve.run();
//                    Debugger.showMatrix(board);
//                    out.writeObject(board);
 
//                }
//                catch(IOException i)
//                {
//                    System.out.println(i);
//                }
//                catch (ClassNotFoundException i){ System.out.println(i);}
         //   }
            System.out.println("Closing connection");
 
            // close connection
            socket.close();
//            in.close();
        }
        catch(IOException i)
        {
            System.out.println(i);
        }
    }
}
