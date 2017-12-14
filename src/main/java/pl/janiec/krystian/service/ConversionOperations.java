package pl.janiec.krystian.service;

import pl.janiec.krystian.nbpApi.NBPResponse;

import java.time.LocalDate;

public interface ConversionOperations {

    NBPResponse convertResponse(String currencyCode, LocalDate date);

    NBPResponse convertResponse(String currencyCode, LocalDate startDate, LocalDate endDate);

}
