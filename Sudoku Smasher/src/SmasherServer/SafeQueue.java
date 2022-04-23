package SmasherServer;

import java.net.Socket;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


//thread safe queue for socket distribution
public class SafeQueue
{
    private Queue<Socket> connection_queue;
    private final Lock lock;
    private final Condition buffer_not_empty;


    public SafeQueue()
    {
        connection_queue = new LinkedList<Socket>();
        lock = new ReentrantLock();
        buffer_not_empty = lock.newCondition();

    }

    /**add a sock
     * @param socket Socket accepted from the main server class
     */
    public void add (Socket socket) throws InterruptedException
    {
        lock.lock();
        try
        {
            connection_queue.add(socket);
            buffer_not_empty.signalAll();

        }
        finally {
            lock.unlock();
        }

    }

    public  Socket take() throws InterruptedException {
        lock.lock();
        try{
            while(connection_queue.size() == 0)
            {
                System.out.println("Thread " + Thread.currentThread().getName() + " is waiting...");
                buffer_not_empty.await();
            }
            Socket next = connection_queue.poll();
            return next;
        }
        finally {
            lock.unlock();
        }
    }
}
