package zagnitko.test_task.entity_ish;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Data object.
 * @author <a href='mailto:htshame@gmail.com'>Artem Zagnitko</a>
 * @version 2018.08.30
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataEntity {

    private BigDecimal amount;

    private Date timestamp;
}
