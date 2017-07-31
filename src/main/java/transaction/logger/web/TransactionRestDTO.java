package transaction.logger.web;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Builder
@Data
@ToString
public class TransactionRestDTO {
    private long timestamp;
    private double amount;
}
