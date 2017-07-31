package transaction.logger.cache;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.TreeMap;

@Repository
public class TransactionCache {
    private TreeMap<Long, Double> cacheMap = new TreeMap<>();
    
    public void cacheTransaction(final long timeStamp, final double amount){
        
    }
    
    public List<Double> getRecentTransactions(final long timestamp){
        return null;
    }
}
