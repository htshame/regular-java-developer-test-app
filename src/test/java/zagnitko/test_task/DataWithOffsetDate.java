package zagnitko.test_task;

import lombok.Data;

import java.math.BigDecimal;

/**
 * Test utility object to create offset of current date in DataEntity object.
 * @author <a href='mailto:htshame@gmail.com'>Artem Zagnitko</a>
 * @version 2018.08.30
 */
@Data
public class DataWithOffsetDate {

    /**
     * Amount to set to DataEntity.amount.
     */
    private BigDecimal amount;

    /**
     * Offset to add to DataEntity.timestamp.
     */
    private Long offset;
}
