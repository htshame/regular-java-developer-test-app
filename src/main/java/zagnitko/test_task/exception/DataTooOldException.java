package zagnitko.test_task.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Data too old exception.
 * @author <a href='mailto:htshame@gmail.com'>Artem Zagnitko</a>
 * @version 2018.08.30
 */
@ResponseStatus(code = HttpStatus.NO_CONTENT)
public class DataTooOldException extends Exception {
    private static final long serialVersionUID = 2169425980798203495L;
}
