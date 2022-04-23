package SmasherServer;

import java.io.ObjectStreamException;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


//thread safe queue for socket distribution
public class SafeQueue<T>
{
    private Queue<T> connection_queue;
    private final Lock lock;
    private final Condition buffer_not_empty;


    public SafeQueue()
    {
        connection_queue = new LinkedList<T>();
        lock = new ReentrantLock();
        buffer_not_empty = lock.newCondition();

    }

    /**add a sock
     * @param thing Socket accepted from the main server class
     */
    public void add (T thing) throws InterruptedException
    {
        lock.lock();
        try
        {
            connection_queue.add(thing);
            buffer_not_empty.signalAll();

        }
        finally {
            lock.unlock();
        }

    }

    public  T take() throws InterruptedException {
        lock.lock();
        try{
            while(connection_queue.size() == 0)
            {
                System.out.println("Thread " + Thread.currentThread().getName() + " is waiting...");
                buffer_not_empty.await();
            }
            T next = connection_queue.poll();
            return next;
        }
        finally {
            lock.unlock();
        }
    }
}
