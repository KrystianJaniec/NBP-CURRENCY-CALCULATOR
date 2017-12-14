package pl.janiec.krystian.service;

import pl.janiec.krystian.nbpApi.NBPResponse;

import java.time.LocalDate;

public interface CurrencyOperations {

    Double getExchangeRate(String currencyCode, LocalDate date);

    Double findMax(NBPResponse nbpResponse);

    Double findMin(NBPResponse nbpResponse);

    Double getAvg(NBPResponse nbpResponse);

    Double getSD(NBPResponse nbpResponse);
}
