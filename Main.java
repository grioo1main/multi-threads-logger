import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
    
    public static void main(String[] args) throws InterruptedException {
         new File("logs").mkdirs();
    LogQueue logQueue = new LogQueue();
    LogSortion filter = new LogSortion(logQueue);
    
    ExecutorService executor = Executors.newFixedThreadPool(6);
    
    executor.submit(new LogGenerator(logQueue));
    executor.submit(filter);
    
    executor.submit(new LogWriter(filter.getErrorQueue(), "logs/error.log"));
    executor.submit(new LogWriter(filter.getInfoQueue(), "logs/info.log"));
    executor.submit(new LogWriter(filter.getWarnQueue(), "logs/warn.log"));
    executor.submit(new LogWriter(filter.getDebugQueue(), "logs/debug.log"));
    
    
    Thread.sleep(10000);
    
    
    executor.shutdown();
    if (executor.awaitTermination(30, TimeUnit.SECONDS)) {
        System.out.println("=== ALL STOPPED OK ===");
    } else {
        executor.shutdownNow();  // Принудительно!
        System.out.println("=== FORCE STOP ===");
    }
}
}
