package SmasherClient;

import java.io.*;
import java.net.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client {
    private Socket socket = null;
    private DataInputStream in = null;
    private DataOutputStream out = null;

    public boolean Client(int port){
        try{
            socket = new Socket("Host", port);
            System.out.println("Client start!");
            System.out.println("Sever-Client are connected!");

            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            return true;

        }catch(IOException e)
        {
            System.out.println("Connection Error!");
        }
        return false;

    }

    public void sentInput(String lines){
        // send input to server
        try {
            out.writeUTF(lines);
        }catch(IOException e) {
            System.out.println("Sending Error");
        }

    }

    public String receiveSolver(){
        String line = "";
        try{
            line = new String(in.readUTF());
        }catch(IOException e){
            System.out.println("Client can not receive message!");
            System.exit(0);
        }
    }
}
