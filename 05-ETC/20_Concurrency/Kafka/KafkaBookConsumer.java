import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class KafkaBookConsumer {

    private final BookService bookService;
    private final RedissonClient redissonClient;

    public KafkaBookConsumer(final BookService bookService, final RedissonClient redissonClient) {
        this.bookService = bookService;
        this.redissonClient = redissonClient;
    }

    @KafkaListener(topics = "book-purchase-topic", groupId = "book-purchase-group")
    public void consume(String message) {
        String[] parts = message.split(",");
        Long bookId = Long.parseLong(parts[0]);
        int quantity = Integer.parseInt(parts[1]);

        RLock lock = redissonClient.getLock(String.format("purchase:book:%d", bookId));
        try {
            boolean available = lock.tryLock(10, 1, TimeUnit.SECONDS);
            if (!available) {
                System.out.println("redisson getLock timeout");
                throw new IllegalArgumentException();
            }
            bookService.purchase(bookId, quantity);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }
}
