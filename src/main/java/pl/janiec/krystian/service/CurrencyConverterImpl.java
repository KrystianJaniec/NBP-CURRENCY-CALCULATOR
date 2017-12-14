package pl.janiec.krystian.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class CurrencyConverterImpl implements CurrencyConverter {

    @Autowired
    private CurrencyOperations currencyOperations;

    @Override
    public Double convertAmount(double amount, LocalDate date, String baseCurrencyCode, String targetCurrencyCode) {

        if (baseCurrencyCode.toLowerCase().equals("pln")) {
            Double baseCurrency = 1.0;
            Double targetCurrency = currencyOperations.getExchangeRate(targetCurrencyCode, date);
            return (baseCurrency / targetCurrency) * amount;

        }
        if (targetCurrencyCode.toLowerCase().equals("pln")) {
            Double baseCurrency = currencyOperations.getExchangeRate(baseCurrencyCode, date);
            Double targetCurrency = 1.0;
            return (baseCurrency / targetCurrency) * amount;
        }
        Double baseCurrency = currencyOperations.getExchangeRate(baseCurrencyCode, date);
        Double targetCurrency = currencyOperations.getExchangeRate(targetCurrencyCode, date);
        return (baseCurrency / targetCurrency) * amount;
    }

}
