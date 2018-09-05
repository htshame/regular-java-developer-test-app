package zagnitko.test_task.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zagnitko.test_task.dto.DataDTO;
import zagnitko.test_task.exception.FieldNotParsableException;
import zagnitko.test_task.exception.DataTimeInTheFutureException;
import zagnitko.test_task.exception.DataTooOldException;
import zagnitko.test_task.service.DataService;

import javax.validation.Valid;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * Data REST controller.
 * @author <a href='mailto:htshame@gmail.com'>Artem Zagnitko</a>
 * @version 2018.08.30
 */
@RestController
@RequestMapping("/data")
@Slf4j
public class DataController {

    /**
     * Data service.
     */
    private final DataService dataService;

    @Autowired
    public DataController(DataService dataService) {
        this.dataService = dataService;
    }

    /**
     * Save data.
     * @param data - data to save.
     * @return HTTP status code.
     * @throws DataTooOldException - thrown if data is older than 1 minute.
     * @throws FieldNotParsableException - thrown if "amount" is not parsable.
     * @throws DataTimeInTheFutureException - thrown if data date is in the future.
     */
    @RequestMapping(value = "", method = POST)
    public ResponseEntity<HttpStatus> addData(@Valid @RequestBody DataDTO data)
            throws DataTooOldException, FieldNotParsableException, DataTimeInTheFutureException {
        log.info("Request to save data with timestamp {} was received.", data.getTimestamp());

        dataService.saveData(data);

        log.info("Request to save data with timestamp {} was executed successfully.",
                data.getTimestamp());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * Delete all data.
     * @return HTTP status code.
     */
    @RequestMapping(value = "", method = DELETE)
    public ResponseEntity<Object> deleteData() {
        log.info("Request to delete all data was received.");
        dataService.deleteAllData();
        log.info("Request to delete all data was executed successfully.");
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
