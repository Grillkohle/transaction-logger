package transaction.logger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TransactionLogger {

    public static void main(final String[] args) {
        SpringApplication.run(TransactionLogger.class, args);
    }
}
