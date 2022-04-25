package SmasherClient;

import java.io.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client {
    private Socket socket = null;
    private DataInputStream in = null;
    private DataOutputStream out = null;
    private final int port;

    public Client(int port){
        this.port = port;
        try{
            socket = new Socket("localhost", port);
            System.out.println("Client start!");
            System.out.println("Sever-Client are connected!");

            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());

        }catch(IOException e)
        {
            System.out.println("Connection Error!");
        }

    }
    /**
     * Sends the 2d array to the Sever to be solved
     * @param board a 2d array representing the current sudoku board
     *
     */
    public void sendPuzzle(int[][] board){
        try {
            ObjectOutputStream obj_out= new ObjectOutputStream(socket.getOutputStream());
            obj_out.writeObject(board);
        }catch (IOException e)  {System.out.println(e);}
    }
    /**
     * Grabs the solved puzzle from the server
     *
     * @return a 2d array
     */
    public int[][] getPuzzle()
    {
        int[][] board = new int[9][9];
        try {
            ObjectInputStream obj_in = new ObjectInputStream(socket.getInputStream());

            board = (int[][]) obj_in.readObject();

        }catch (IOException e){System.out.println(e);}
        catch (ClassNotFoundException e) {e.printStackTrace();}
        return board;
    }
    /**
     * Resets the server/client connection everytime the puzzle gets solved or validated.
     */
    public void setSocket(){
        try {
            socket = new Socket("localhost", port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
