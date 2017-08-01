package transaction.logger.cache;

import org.springframework.stereotype.Repository;
import transaction.logger.web.StatisticsRestDTO;
import transaction.logger.web.TransactionRestDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.stream.Collectors;

import static java.lang.String.format;

@Repository
public class TransactionCache {
    private TreeMap<Long, List<Double>> activeTransactions = new TreeMap<>();
    
    private double min;
    private double max;
    private double average;
    private double sum;
    private long count;
    
    public void cacheTransaction(final long timeStamp, final double amount){
        synchronized (activeTransactions){
            List<Double> timestampAmounts = activeTransactions.get(timeStamp);
            if (timestampAmounts == null){
                timestampAmounts = new ArrayList<>();
            }
            timestampAmounts.add(amount);
            activeTransactions.put(timeStamp, timestampAmounts);
            count++;
            
            final List<Double> activeAmounts = activeTransactions.values().stream()
                    .flatMap(List::stream)
                    .sorted()
                    .collect(Collectors.toList());
            
            recalculateStatistics(activeAmounts);
        }
    }

    public void timeoutTransaction(final double amount, final long timestamp) {
        synchronized (activeTransactions) {
            count--;

            final List<Double> amountsForTimestamp = activeTransactions.get(timestamp);

            if (amountsForTimestamp == null || amountsForTimestamp.get(0) == null) {
                throw new RuntimeException(format(
                        "Failed to timeout transaction: Transaction for timestamp %d and amount %f that should be present!",
                        timestamp,
                        amount));
            }

            final boolean removedAmount = amountsForTimestamp.remove(amount);

            if (!removedAmount) {
                throw new RuntimeException(format(
                        "Failed to remove amount %f for timestamp %d",
                        amount,
                        timestamp));
            }

            final List<Double> activeAmounts = activeTransactions.values().stream()
                    .flatMap(List::stream)
                    .sorted()
                    .collect(Collectors.toList());

            recalculateStatistics(activeAmounts);
        }
    }

    private void recalculateStatistics(final List<Double> activeAmounts) {
        if (count == 0){
            this.min = 0;
            this.max = 0;
            this.average = 0;
            this.sum = 0;
            return;
        }
        
        this.min = activeAmounts.get(0);
        this.max = activeAmounts.get(activeAmounts.size() - 1);
        this.sum = activeAmounts.stream().mapToDouble(Double::doubleValue).sum();
        this.average = sum / count;
    }
    
    public StatisticsRestDTO getStatistics(){
        synchronized (activeTransactions) {
            return StatisticsRestDTO.builder()
                    .avg(average)
                    .sum(sum)
                    .min(min)
                    .max(max)
                    .count(count)
                    .build();
        }
    }
}