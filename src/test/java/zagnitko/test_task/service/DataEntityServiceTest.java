package zagnitko.test_task.service;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import zagnitko.test_task.database_ish.Storage;
import zagnitko.test_task.dto.DataDTO;
import zagnitko.test_task.exception.FieldNotParsableException;
import zagnitko.test_task.exception.DataTimeInTheFutureException;
import zagnitko.test_task.exception.DataTooOldException;

import java.util.Date;

/**
 * Data service test.
 * @author <a href='mailto:htshame@gmail.com'>Artem Zagnitko</a>
 * @version 2018.08.30
 */
@RunWith(SpringRunner.class)
public class DataEntityServiceTest {

    @InjectMocks
    private DataService dataService;

    @Spy
    private Storage storage;

    private static final int MINUTES_TO_SUBTRACT = 1;

    private static final int MINUTES_IN_THE_PAST = 2;

    /**
     * Clean storage before the test.
     */
    @Before
    public void before() {
        storage.clearStorage();
    }

    /**
     * Clean storage after the test.
     */
    @After
    public void after() {
        storage.clearStorage();
    }

    /**
     * Save data successfully.
     */
    @Test
    public void testSaveDataSuccess() {
        ReflectionTestUtils.setField(dataService, "MINUTES_TO_SUBTRACT", MINUTES_TO_SUBTRACT);
        DataDTO dataDTO = new DataDTO("123.45", new Date());
        try {
            dataService.saveData(dataDTO);
            Assert.assertEquals(1, storage.getRecordsNumber());
        } catch (FieldNotParsableException | DataTooOldException | DataTimeInTheFutureException e) {
            Assert.fail();
        }
    }

    /**
     * Save data with non-BigDecimal.
     */
    @Test
    public void testSaveDataNoBigDecimal() {
        ReflectionTestUtils.setField(dataService, "MINUTES_TO_SUBTRACT", MINUTES_TO_SUBTRACT);
        DataDTO dataDTO = new DataDTO("big decimal", new Date());
        try {
            try {
                dataService.saveData(dataDTO);
                Assert.fail();
            } catch (DataTooOldException | DataTimeInTheFutureException e) {
                Assert.fail();
            }
        } catch (FieldNotParsableException e) {
            Assert.assertEquals(0, storage.getRecordsNumber());
        }
    }

    /**
     * Save too old data.
     */
    @Test
    public void testSaveDataDateTooOld() {
        ReflectionTestUtils.setField(dataService, "MINUTES_TO_SUBTRACT", MINUTES_TO_SUBTRACT);
        DataDTO dataDTO =
                new DataDTO("123.45", dataService.minutesInThePast(MINUTES_IN_THE_PAST));
        try {
            dataService.saveData(dataDTO);
            Assert.fail();
        } catch (DataTooOldException e) {
            Assert.assertEquals(0, storage.getRecordsNumber());
        } catch (FieldNotParsableException | DataTimeInTheFutureException e) {
            Assert.fail();
        }
    }

    /**
     * Save data with future date.
     */
    @Test
    public void testSaveDataDateFromTheFuture() {
        ReflectionTestUtils.setField(dataService, "MINUTES_TO_SUBTRACT", MINUTES_TO_SUBTRACT);
        DataDTO dataDTO =
                new DataDTO("123.45", dataService.minutesInThePast(-MINUTES_IN_THE_PAST));
        try {
            dataService.saveData(dataDTO);
            Assert.fail();
        } catch (DataTooOldException | FieldNotParsableException e) {
            Assert.fail();
        } catch (DataTimeInTheFutureException e) {
            Assert.assertEquals(0, storage.getRecordsNumber());
        }
    }

    /**
     * Delete all data.
     */
    @Test
    public void testDeleteAllData() {
        ReflectionTestUtils.setField(dataService, "MINUTES_TO_SUBTRACT", MINUTES_TO_SUBTRACT);
        DataDTO dataDTO1 = new DataDTO("123.45", new Date());
        DataDTO dataDTO2 = new DataDTO("123.45", new Date());
        try {
            dataService.saveData(dataDTO1);
            dataService.saveData(dataDTO2);
        } catch (FieldNotParsableException | DataTimeInTheFutureException | DataTooOldException e) {
            Assert.fail();
        }
        Assert.assertEquals(2, storage.getRecordsNumber());
        dataService.deleteAllData();
        Assert.assertEquals(0, storage.getRecordsNumber());
    }
}
