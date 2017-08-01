package transaction.logger.web;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StatisticsRestDTO {
    private long min;
    private long max;
    private long avg;
    private long sum;
    private long count;
}
