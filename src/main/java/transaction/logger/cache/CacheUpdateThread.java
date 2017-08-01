package transaction.logger.cache;

import lombok.extern.slf4j.Slf4j;
import transaction.logger.web.TransactionRestDTO;

import static transaction.logger.service.TransactionService.ONE_MINUTE;

@Slf4j
public class CacheUpdateThread implements Runnable {
    private final TransactionCache cache;
    private final double amount;
    private final long timestamp;

    public CacheUpdateThread(final TransactionCache cache, final double amount, final long timestamp) {
        this.cache = cache;
        this.amount = amount;
        this.timestamp = timestamp;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(ONE_MINUTE);
        } catch (InterruptedException e) {
            log.error("Update thread was interrupted.");
        }
        cache.timeoutTransaction(amount, timestamp);
    }
}
