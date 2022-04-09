package SmasherServer;

import java.net.Socket;
import java.util.ArrayList;


public class SafeQueue
{
    private ArrayList<Socket> connection_queue;

    public SafeQueue()
    {
        connection_queue = new ArrayList<>();
    }

    public synchronized void add(Socket socket)
    {
        connection_queue.add(socket);
    }

    public synchronized Socket take()
    {
        if(connection_queue.size()>0)
        {
            return connection_queue.remove(0);
        }
        else
        {
            //should block here;
            return null;
        }
    }
}
