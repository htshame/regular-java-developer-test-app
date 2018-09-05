package zagnitko.test_task;

import com.fasterxml.jackson.databind.ObjectMapper;
import zagnitko.test_task.entity_ish.DataEntity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Test utility class to read test data from json files.
 * @author <a href='mailto:htshame@gmail.com'>Artem Zagnitko</a>
 * @version 2018.08.30
 */
public class TestUtilities {

    /**
     * Read data for list of objects.
     * @param name - file name.
     * @param relatedToClass - test class the data related to.
     * @param readAsClass - class which the data is for.
     * @param <T> - generic type.
     * @return List<WhereverObjectYouProvide>.
     */
    public static <T> List<T> readDataForListOfObjects(String name, Class<?> relatedToClass, Class<T> readAsClass) {
        ObjectMapper mapper = new ObjectMapper();
        List<T> retList = new ArrayList<>();
        try {
            mapper.reader().forType(readAsClass).readTree(relatedToClass.getResourceAsStream(name)).forEach(node -> {
                try {
                    retList.add(mapper.reader().forType(readAsClass).readValue(node));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            return retList;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Create DataEntity object from DataWithOffsetDate object.
     * @param offset - DataWithOffsetDate object.
     * @return DataEntity object.
     */
    public static DataEntity createDataEntityFromOffsetDate(DataWithOffsetDate offset) {
        Date dateToSet = new Date(Calendar.getInstance().getTimeInMillis() - offset.getOffset());
        return new DataEntity(offset.getAmount(), dateToSet);
    }
}
