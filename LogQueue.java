import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class LogQueue {

    Queue<LogEntity> queue = new ArrayDeque<>();
    
    private final ReentrantLock lock = new ReentrantLock();
    private final Condition notFull = lock.newCondition();
    private final Condition notEmpty = lock.newCondition();

    public void addLog(LogEntity log) throws InterruptedException{
        lock.lock();
        try {
            while (queue.size() == 1000){
                notEmpty.await();
            }
            queue.add(log);
            notEmpty.signalAll();
        } finally {
            lock.unlock();
        }
    }
    public LogEntity getLog() throws InterruptedException {
        lock.lock();
        try {
            while (queue.isEmpty()) {
                notEmpty.await();
            }
            LogEntity log = queue.poll();
            notFull.signalAll();
            return log;
        } finally {
            lock.unlock();
        }

    }
}