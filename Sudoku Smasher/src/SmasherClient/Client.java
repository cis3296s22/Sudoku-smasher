package SmasherClient;

import javax.management.ObjectInstance;
import java.io.*;
import java.net.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.security.PublicKey;

public class Client {
    private Socket socket = null;
    private DataInputStream in = null;
    private DataOutputStream out = null;
    private String serverHost = "localhost";
    private int serverPort = 3000;

    /**
     * Makes a new client and establishes connection
     * @param port port to be used for communication
     */
    public Client(int port){
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
     * sends a puzzle to the server for solving
     * @param board 9x9 int array representing sudoku boards
     */
    public void sendPuzzle(int[][] board){
        try {
            ObjectOutputStream obj_out= new ObjectOutputStream(socket.getOutputStream());
            obj_out.writeObject(board);
        }catch (IOException e)  {System.out.println(e);}
    }

    /**
     * sends a message to server, used for debugging purposes
     * @param lines message to be sent to server
     */
    public void sendInput(String lines){
        // send input to server
        try {
            out.writeUTF(lines);
        }catch(IOException e) {
            System.out.println("Sending Error");
        }

    }

    /**
     * receive a sudoku boards from the server
     * @return sudoku board received from server
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
     * receive a message, used for debugging purposes
     * @return message sent from server
     */

    public String receiveSolver(){
        String line = "";
        try{
            line = new String(in.readUTF());
        }catch(IOException e){
            System.out.println("Client can not receive message!");
            System.exit(0);
        }
        return line;
    }
}
