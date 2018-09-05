package zagnitko.test_task.service;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import zagnitko.test_task.TestUtilities;
import zagnitko.test_task.DataWithOffsetDate;
import zagnitko.test_task.database_ish.Storage;
import zagnitko.test_task.dto.InformationDTO;

import java.time.Duration;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

/**
 * Information service test.
 * @author <a href='mailto:htshame@gmail.com'>Artem Zagnitko</a>
 * @version 2018.08.30
 */
@RunWith(SpringRunner.class)
public class InformationServiceTest {

    @InjectMocks
    private InformationService informationService;

    @Mock
    private DataService dataService;

    @Spy
    private Storage storage;

    private static final int MINUTES_TO_SUBTRACT = 1;

    private static final int ROUND_UP_TO = 2;

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
     * Get information from empty storage.
     */
    @Test
    public void testGetInformationEmpty() {
        ReflectionTestUtils.setField(informationService, "MINUTES_TO_SUBTRACT", MINUTES_TO_SUBTRACT);
        ReflectionTestUtils.setField(informationService, "ROUND_UP_TO", ROUND_UP_TO);
        doReturn(new Date()).when(dataService).minutesInThePast(any(Integer.class));
        InformationDTO stats = informationService.getInformation();
        Assert.assertEquals("0.00", stats.getAvg());
        Assert.assertEquals("0.00", stats.getSum());
        Assert.assertEquals("0.00", stats.getMax());
        Assert.assertEquals("0.00", stats.getMin());
        Assert.assertEquals(Integer.valueOf(0), stats.getCount());
    }

    /**
     * Get information with data in storage.
     */
    @Test
    public void testGetInformation() {
        ReflectionTestUtils.setField(informationService, "MINUTES_TO_SUBTRACT", MINUTES_TO_SUBTRACT);
        ReflectionTestUtils.setField(informationService, "ROUND_UP_TO", ROUND_UP_TO);
        Date dateInThePast =
                new Date(Calendar.getInstance().getTimeInMillis() - Duration.ofMinutes(MINUTES_TO_SUBTRACT).toMillis());
        doReturn(dateInThePast).when(dataService).minutesInThePast(any(Integer.class));
        populateStorage();
        InformationDTO stats = informationService.getInformation();
        Assert.assertEquals("988.46", stats.getAvg());
        Assert.assertEquals("4942.28", stats.getSum());
        Assert.assertEquals("3258.67", stats.getMax());
        Assert.assertEquals("125.32", stats.getMin());
        Assert.assertEquals(Integer.valueOf(5), stats.getCount());
    }

    /**
     * Populate storage with data.
     */
    private void populateStorage() {
        List<DataWithOffsetDate> dataWithOffset = TestUtilities.readDataForListOfObjects(
                "dataList.json", InformationServiceTest.class, DataWithOffsetDate.class);
        for (DataWithOffsetDate offset : dataWithOffset) {
            storage.addData(TestUtilities.createDataEntityFromOffsetDate(offset));
        }
    }
}
