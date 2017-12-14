package pl.janiec.krystian.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import pl.janiec.krystian.nbpApi.NBPResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ConversionOperationsImpl implements ConversionOperations {

    private static final Logger log = LoggerFactory.getLogger(ConversionOperationsImpl.class);

    private static final DateTimeFormatter DTF = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private static final String URL_DATE = "http://api.nbp.pl/api/exchangerates/rates/A/{code}/{date}/?format=json";
    private static final String URL_BETWEEN_DATES = "http://api.nbp.pl/api/exchangerates/rates/A/{code}/{startDate}/{endDate}/?format=json";

    private RestTemplate restTemplate = new RestTemplate();

    @Override
    public NBPResponse convertResponse(String currencyCode, LocalDate date) {
        String formattedDate = DTF.format(date);

        NBPResponse response = restTemplate.getForObject(URL_DATE, NBPResponse.class, currencyCode, formattedDate);
        log.debug("response", response);

        return response;
    }

    @Override
    public NBPResponse convertResponse(String currencyCode, LocalDate startDate, LocalDate endDate) {
        String formattedStartDate = DTF.format(startDate);
        String formattedEndDate = DTF.format(endDate);

        NBPResponse response = restTemplate.getForObject(URL_BETWEEN_DATES, NBPResponse.class, currencyCode, formattedStartDate, formattedEndDate);
        log.debug("response", response);

        return response;
    }

}
