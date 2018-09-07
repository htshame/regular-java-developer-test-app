package zagnitko.test_task.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zagnitko.test_task.dto.InformationDTO;
import zagnitko.test_task.service.InformationService;

import java.util.Date;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * Information REST controller.
 * @author <a href='mailto:htshame@gmail.com'>Artem Zagnitko</a>
 * @version 2018.08.30
 */
@RestController
@RequestMapping("/information")
@Slf4j
public class InformationController {

    /**
     * Information service.
     */
    private final InformationService informationService;

    /**
     * Constructor.
     * @param informationService - information service.
     */
    @Autowired
    public InformationController(InformationService informationService) {
        this.informationService = informationService;
    }

    /**
     * Get data for last 60 seconds.
     * @return information about data for last 60 seconds.
     */
    @RequestMapping(value = "", method = GET)
    public ResponseEntity<InformationDTO> getInformation() {
        Date date = new Date();
        log.info("Request to get information within last 60 seconds, starting from {} was received.", date);

        InformationDTO information = informationService.getInformation();

        log.info("Request to get information within last 60 seconds, starting from {} was executed successfully.",
                date);
        return ResponseEntity.ok(information);
    }
}
