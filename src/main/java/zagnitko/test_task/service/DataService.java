package zagnitko.test_task.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import zagnitko.test_task.database_ish.Storage;
import zagnitko.test_task.dto.DataDTO;
import zagnitko.test_task.entity_ish.DataEntity;
import zagnitko.test_task.exception.FieldNotParsableException;
import zagnitko.test_task.exception.DataTimeInTheFutureException;
import zagnitko.test_task.exception.DataTooOldException;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Calendar;
import java.util.Date;

/**
 * Data service.
 * @author <a href='mailto:htshame@gmail.com'>Artem Zagnitko</a>
 * @version 2018.08.30
 */
@Component
@Slf4j
public class DataService {

    /**
     * Minutes to subtract.
     */
    @Value("${minutes.to.subtract}")
    private int MINUTES_TO_SUBTRACT;

    private final Storage storage;

    @Autowired
    public DataService(Storage storage) {
        this.storage = storage;
    }

    /**
     * Save data.
     * @param dataDTO - data.
     * @throws FieldNotParsableException - if field is non-parsable.
     * @throws DataTooOldException - if data is too old.
     * @throws DataTimeInTheFutureException - if data time is in the future.
     */
    public void saveData(DataDTO dataDTO) throws FieldNotParsableException,
            DataTooOldException, DataTimeInTheFutureException {
        Date date = dataDTO.getTimestamp();

        log.info("Saving data with timestamp {}.", date);

        if (date.before(minutesInThePast(MINUTES_TO_SUBTRACT))) {
            log.error("Data with timestamp {} is too old.", date);
            throw new DataTooOldException();
        }
        if (date.after(new Date(Calendar.getInstance().getTimeInMillis()))) {
            log.error("Data timestamp {} is in the future.", date);
            throw new DataTimeInTheFutureException();
        }

        BigDecimal amountDecimal;
        try {
            amountDecimal = new BigDecimal(dataDTO.getAmount());
        } catch (NumberFormatException e) {
            log.error("Error parsing data's amount with timestamp {} to BigDecimal.",
                    dataDTO.getTimestamp());
            throw new FieldNotParsableException();
        }
        storage.addData(new DataEntity(amountDecimal, date));
        log.info("Data with timestamp {} was saved successfully.", date);
    }

    /**
     * Delete all data.
     */
    public void deleteAllData() {
        log.info("Deleting all data.");
        storage.clearStorage();
        log.info("All data was deleted.");
    }

    /**
     * Get date minus specified number of minutes.
     * @param minutes - number of minutes to subtract.
     * @return one minute in the past in Zulu timezone.
     */
    Date minutesInThePast(int minutes) {
        return new Date(Calendar.getInstance().getTimeInMillis() - Duration.ofMinutes(minutes).toMillis());
    }
}
