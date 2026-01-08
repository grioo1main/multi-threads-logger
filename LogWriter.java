import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.concurrent.BlockingQueue;

public class LogWriter implements Runnable {
    BlockingQueue<LogEntity> queue;
    String filename;

    public LogWriter(BlockingQueue<LogEntity> queue, String filename) {
        this.queue = queue;
        this.filename = filename;
    }

    @Override
    public void run() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
           while (!Thread.currentThread().isInterrupted()) {
            try {
                    LogEntity log = queue.take();
                    writer.write(log.toString());
                    writer.newLine();
                    writer.flush();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.out.println("Innterrupted");
                    break;
                    
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
