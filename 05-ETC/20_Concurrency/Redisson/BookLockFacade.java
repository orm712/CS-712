@Service
public class BookLockFacade {

    private final BookService bookService;
    private final RedissonClient redissonClient;

    public BookLockFacade(final BookService bookService, final RedissonClient redissonClient) {
        this.bookService = bookService;
        this.redissonClient = redissonClient;
    }

    public void purchase(final Long bookId, final int quantity) {
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