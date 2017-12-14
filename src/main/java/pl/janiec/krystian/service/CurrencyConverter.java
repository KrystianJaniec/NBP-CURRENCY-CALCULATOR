package pl.janiec.krystian.service;

import java.time.LocalDate;

public interface CurrencyConverter {

    Double convertAmount(double amount, LocalDate date, String baseCurrencyCode, String targetCurrencyCode);

}
