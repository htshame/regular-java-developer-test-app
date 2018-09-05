package zagnitko.test_task.database_ish;

import org.springframework.stereotype.Service;
import zagnitko.test_task.entity_ish.DataEntity;

import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

/**
 * Database-ish storage class.
 * @author <a href='mailto:htshame@gmail.com'>Artem Zagnitko</a>
 * @version 2018.08.30
 */
@Service
public class Storage {

    /**
     * Database-ish storage.
     */
    private static List<DataEntity> STORAGE = new CopyOnWriteArrayList<>();

    /**
     * Add data to storage.
     * @param dataEntity - data to add.
     */
    public void addData(DataEntity dataEntity) {
        STORAGE.add(dataEntity);
    }

    /**
     * Delete all data.
     */
    public void clearStorage() {
        STORAGE.clear();
    }

    /**
     * Get stored data since provided date.
     * @param date - date since get data from storage.
     * @return stored data.
     */
    public List<DataEntity> getStoredDataSinceDate(Date date) {
        return STORAGE.stream().filter(p -> (p.getTimestamp().after(date))).collect(Collectors.toList());
    }

    /**
     * Get number of records in storage.
     * @return number of records in storage.
     */
    public int getRecordsNumber() {
        return STORAGE.size();
    }
}
