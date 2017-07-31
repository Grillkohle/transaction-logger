package transaction.logger.cache;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

@Repository
public class TransactionCache {
    private TreeMap<Long, List<Double>> cacheMap = new TreeMap<>();
    
    public synchronized void cacheTransaction(final long timeStamp, final double amount){
        List<Double> timestampAmounts = cacheMap.get(timeStamp);
        if (timestampAmounts == null){
            timestampAmounts = new ArrayList<>();
        }
        timestampAmounts.add(amount);
        
        cacheMap.put(timeStamp, timestampAmounts);
    }
    
    public synchronized List<Double> getRecentTransactions(final long timestamp){
        return null;
    }
}
