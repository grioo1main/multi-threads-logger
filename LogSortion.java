import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class LogSortion implements Runnable {
    private LogQueue logQueue;
    private BlockingQueue<LogEntity> errorQueue = new ArrayBlockingQueue<>(100);
    private BlockingQueue<LogEntity> infoQueue = new ArrayBlockingQueue<>(100);
    private BlockingQueue<LogEntity> warnQueue = new ArrayBlockingQueue<>(100);
    private BlockingQueue<LogEntity> debugQueue = new ArrayBlockingQueue<>(100);

    public BlockingQueue<LogEntity> getErrorQueue() {
        return errorQueue;
    }

    public BlockingQueue<LogEntity> getInfoQueue() {
        return infoQueue;
    }

    public BlockingQueue<LogEntity> getWarnQueue() {
        return warnQueue;
    }

    public BlockingQueue<LogEntity> getDebugQueue() {
        return debugQueue;
    }

    public LogSortion(LogQueue logQueue) {
        this.logQueue = logQueue;
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    LogEntity log = logQueue.getLog(); 

                    logSortion(log); 
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void logSortion(LogEntity log) throws InterruptedException {
        switch (log.getType()) {
            case "ERROR" -> errorQueue.put(log); 
            case "INFO" -> infoQueue.put(log);
            case "WARN" -> warnQueue.put(log);
            default -> debugQueue.put(log);
        }
    }
}
