package transaction.logger.cache;

import lombok.extern.slf4j.Slf4j;
import transaction.logger.web.TransactionRestDTO;

import static transaction.logger.service.TransactionService.ONE_MINUTE;

@Slf4j
public class CacheUpdateThread extends Thread {
    private final TransactionCache cache;
    private final double amount;
    private final long timestamp;
    private final long timeout;

    public CacheUpdateThread(final TransactionCache cache, final double amount, final long timestamp, final long timeout) {
        this.cache = cache;
        this.amount = amount;
        this.timestamp = timestamp;
        this.timeout = timeout;
    }

    @Override
    public void run() {
        super.run();
        
        try {
            log.info("Scheduling timeout for amount {} with timestamp {} in {} milliseconds.", amount, timestamp, timeout);
            Thread.sleep(timeout);
        } catch (InterruptedException e) {
            log.error("Update thread was interrupted.");
        }
        cache.timeoutTransaction(amount, timestamp);
    }
}
