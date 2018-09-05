package zagnitko.test_task.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import zagnitko.test_task.database_ish.Storage;
import zagnitko.test_task.dto.InformationDTO;
import zagnitko.test_task.entity_ish.DataEntity;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * Information service.
 * @author <a href='mailto:htshame@gmail.com'>Artem Zagnitko</a>
 * @version 2018.08.30
 */
@Component
@Slf4j
public class InformationService {

    /**
     * Minutes to subtract.
     */
    @Value("${minutes.to.subtract}")
    private int MINUTES_TO_SUBTRACT;

    /**
     * Number to round up to.
     */
    @Value("${round.up.bigdecimal.to}")
    private int ROUND_UP_TO;

    private final DataService dataService;
    private final Storage storage;

    @Autowired
    public InformationService(DataService dataService, Storage storage) {
        this.dataService = dataService;
        this.storage = storage;
    }

    /**
     * Get statistics information for the last minute.
     * @return statistics information data.
     */
    public InformationDTO getInformation() {
        Date lastMinuteDate = dataService.minutesInThePast(MINUTES_TO_SUBTRACT);
        log.info("Collecting statistics information since {}.", lastMinuteDate);
        BigDecimal sum = BigDecimal.ZERO;
        BigDecimal avg = BigDecimal.ZERO;
        BigDecimal max = BigDecimal.ZERO;
        BigDecimal min = BigDecimal.ZERO;
        int count = 0;
        List<DataEntity> dataEntityForLastMinute = storage.getStoredDataSinceDate(lastMinuteDate);

        if (!dataEntityForLastMinute.isEmpty()) {
            DataEntity maxDataEntity = Collections.max(dataEntityForLastMinute,
                    Comparator.comparing(DataEntity::getAmount));
            DataEntity minDataEntity = Collections.min(dataEntityForLastMinute,
                    Comparator.comparing(DataEntity::getAmount));

            sum = BigDecimal.ZERO;
            for (DataEntity dataEntity : dataEntityForLastMinute) {
                sum = sum.add(dataEntity.getAmount());
            }

            count = dataEntityForLastMinute.size();
            avg = sum.divide(BigDecimal.valueOf(count), ROUND_UP_TO, BigDecimal.ROUND_HALF_UP);

            max = maxDataEntity.getAmount();
            min = minDataEntity.getAmount();
            log.info("Data statistics information since {} was collected successfully.", lastMinuteDate);
        } else {
            log.info("No data records were found since {}.", lastMinuteDate);
        }
        return new InformationDTO(round(sum).toString(), round(avg).toString(), round(max).toString(),
                round(min).toString(), count);
    }

    /**
     * Round BigDecimal.
     * @param num - BigDecimal to round.
     * @return rounded BigDecimal.
     */
    private BigDecimal round(BigDecimal num) {
        return num.setScale(ROUND_UP_TO, BigDecimal.ROUND_HALF_UP);
    }
}
