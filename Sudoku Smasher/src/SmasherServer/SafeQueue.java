package SmasherServer;

import java.net.Socket;
import java.util.ArrayList;

import java.util.Scanner;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


//thread safe queue for socket distribution
public class SafeQueue
{
    private ArrayList<Socket> connection_queue;
    private final int max_threads;
    private int solvers_running;
    private final Semaphore sem;
    private final Lock lock;
    private final Condition not_empty;
    private final Condition not_full;

    public SafeQueue(int max_threads)
    {
        connection_queue = new ArrayList<>();
        this.max_threads = max_threads;
        this.solvers_running = 0;
        sem = new Semaphore(max_threads, true);
        this.lock = new ReentrantLock();
        this.not_empty = lock.newCondition();
        this.not_full = lock.newCondition();
    }

    /**add a sock
     * @param socket Socket accepted from the main server class
     */
    public synchronized void add (Socket socket) throws InterruptedException
    {
        lock.lock();
        try
        {
            while(solvers_running<max_threads)
                not_full.await();

            solvers_running--;
            connection_queue.add(socket);
            not_empty.signalAll();
        }
        finally { lock.unlock(); }

    }

    public synchronized Socket take() throws InterruptedException {
        lock.lock();
        try
        {
            while(solvers_running == 0)
                not_empty.await();

            not_full.signalAll();

            solvers_running++;
            return connection_queue.remove(0);
        }
        finally { lock.unlock(); }
    }
}
