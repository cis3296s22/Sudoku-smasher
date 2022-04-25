package SmasherServer;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


/**Thread-safe FIFO queue for distritbution of objects from thread to thread
 *
 * @param <T> any Type
 * connection_queue is a queue holding objects
 * lock is a mutex lock used to maintain concurrency
 * buffer_not_empty is a condition variable signalling the queue holds items
 */
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

    /**add an item to the queue
     * @param thing object to be added to the queue
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

    /**Take an item from the queue.
     *
     * @return object of type T
     * @throws InterruptedException if thread interrupted
     */
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
