import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LogGenerator implements Runnable {  
    private LogQueue logQueue; 
    
    public LogGenerator(LogQueue logQueue) {
        this.logQueue = logQueue;
    }
    
    public void generateLog(Integer i) throws InterruptedException {
        List<String> list = new ArrayList<>(List.of("ERROR", "WARN", "INFO", "DEBUG")); 
        
        for (int j = 0; j < i; j++) {
            Collections.shuffle(list);
            logQueue.addLog(new LogEntity(list.get(0), "message " + j));
        }
    }
    
    @Override
    public void run() { 
        try {
            generateLog(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
