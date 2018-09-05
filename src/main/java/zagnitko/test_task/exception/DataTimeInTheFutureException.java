package zagnitko.test_task.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Data time is in the future exception.
 * @author <a href='mailto:htshame@gmail.com'>Artem Zagnitko</a>
 * @version 2018.08.30
 */
@ResponseStatus(code = HttpStatus.UNPROCESSABLE_ENTITY)
public class DataTimeInTheFutureException extends Exception {
    private static final long serialVersionUID = 6108126597606908680L;
}
