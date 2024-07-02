public class BookTest {

    @Test
    void 동시에_100명이_책을_구매한다() throws InterruptedException {
        Long bookId = bookRepository.save(new Book("이펙티브 자바", 36_000, new Stock(100)))
                .getId();
        //병렬 작업 시 여러 개의 작업을 효율적으로 처리하기 위해 제공되는 JAVA 라이브러리
        //100개의 쓰레드 풀로 실행
        ExecutorService executorService = Executors.newFixedThreadPool(100);

        //어떤 쓰레드가 다른 쓰레드에서 작업이 완료될 때 까지 기다릴 수 있도록 해주는 클래스
        CountDownLatch countDownLatch = new CountDownLatch(100);

        for (int i = 0; i < 100; i++) {
            executorService.submit(() -> {
                try {
                    bookService.purchase(bookId, 1);
                } finally {
                    countDownLatch.countDown();
                }
            });
        }

        countDownLatch.await();

        Book actual = bookRepository.findById(bookId)
                .orElseThrow();

        assertThat(actual.getStock().getRemain()).isZero();
    }
}