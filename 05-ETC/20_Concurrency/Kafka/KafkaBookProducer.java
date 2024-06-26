@Service
public class KafkaBookProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public KafkaBookProducer(final KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void purchase(final Long bookId, final int quantity) {
        String message = bookId + "," + quantity;
        kafkaTemplate.send("book-purchase-topic", message);
    }
}
