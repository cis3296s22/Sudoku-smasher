package SmasherServer;

import java.net.Socket;
import java.util.ArrayList;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Semaphore;

//thread safe queue for socket distribution
public class SafeQueue
{
    private ConcurrentLinkedQueue<Socket> connection_queue;
    private int solvers_running;
    private final Semaphore jobs_available;


    public SafeQueue()
    {
        connection_queue = new ConcurrentLinkedQueue<Socket>();
        this.solvers_running = 0;
        jobs_available = new Semaphore(solvers_running, true);

    }

    /**add a sock
     * @param socket Socket accepted from the main server class
     */
    public  void add (Socket socket) throws InterruptedException
    {
        jobs_available.release();
        connection_queue.add(socket);
    }

    public  Socket take() throws InterruptedException {
        jobs_available.acquire();
        return connection_queue.poll();
    }
}
