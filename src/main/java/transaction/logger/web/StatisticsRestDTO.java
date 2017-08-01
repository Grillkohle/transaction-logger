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
    private double min;
    private double max;
    private double avg;
    private double sum;
    private long count;
}
