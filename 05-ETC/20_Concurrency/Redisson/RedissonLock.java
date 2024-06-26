
@Service
public class RedissonLock {

    private final BookRepository bookRepository;

    public BookService(final BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    //도서 구매에 성공하면 재고 1 감소
    //재고 부족하면 예외 반환
    @Transactional
    public void purchase(final Long bookId, final long quantity) {
        //Redisson의 Lock 객체
        RLock lock = redissonClient.getLock(String.format("purchase:book:%d", bookId));
        try {
            boolean available = lock.tryLock(10, 1, TimeUnit.SECONDS);
            if (!available) {
                System.out.println("redisson getLock timeout");
                return;
            }
            Book book = bookRepository.findById(bookId)
                    .orElseThrow(IllegalArgumentException::new);
            book.purchase(quantity);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }


}