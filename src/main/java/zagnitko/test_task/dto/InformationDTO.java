package zagnitko.test_task.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Information DTO.
 * @author <a href='mailto:htshame@gmail.com'>Artem Zagnitko</a>
 * @version 2018.08.30
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InformationDTO {

    /**
     * Total sum of data values in the last 60 seconds.
     */
    private String sum;

    /**
     * Average amount of data values in the last 60 seconds.
     */
    private String avg;

    /**
     * Highest data values in the last 60 seconds.
     */
    private String max;

    /**
     * Lowest data value in the last 60 seconds.
     */
    private String min;

    /**
     * Total number of data records for last 60 seconds.
     */
    private Integer count;
}
