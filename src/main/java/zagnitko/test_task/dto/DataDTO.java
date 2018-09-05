package zagnitko.test_task.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Data DTO.
 * @author <a href='mailto:htshame@gmail.com'>Artem Zagnitko</a>
 * @version 2018.08.30
 */
@Data
@AllArgsConstructor
public class DataDTO {

    /**
     * Data amount.
     */
    @NotNull
    @NotEmpty
    private String amount;

    /**
     * Data time.
     */
    @NotNull
    private Date timestamp;
}
