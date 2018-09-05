package zagnitko.test_task.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Field is not parsable exception.
 * @author <a href='mailto:htshame@gmail.com'>Artem Zagnitko</a>
 * @version 2018.08.30
 */
@ResponseStatus(code = HttpStatus.UNPROCESSABLE_ENTITY)
public class FieldNotParsableException extends Exception {
    private static final long serialVersionUID = -3678374720468007718L;
}
