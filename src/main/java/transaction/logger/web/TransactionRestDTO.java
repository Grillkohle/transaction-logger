package transaction.logger.web;

import lombok.Data;

@Data
public class TransactionRestDTO {
    private long timestamp;
    private double amount;
}
