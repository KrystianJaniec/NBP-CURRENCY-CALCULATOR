package pl.janiec.krystian.service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.OptionalDouble;

import pl.janiec.krystian.nbpApi.NBPResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.janiec.krystian.nbpApi.Rate;

@Service
public class CurrencyOperationsImpl implements CurrencyOperations {

    @Autowired
    private ConversionOperations conversionOperations;

    @Override
    public Double getExchangeRate(String currencyCode, LocalDate date) {
        return conversionOperations.convertResponse(currencyCode, date)
                .getRates()
                .stream()
                .map(Rate::getMid)
                .findFirst().get();
    }

    @Override
    public Double findMax(NBPResponse nbpResponse) {
        List<Rate> list = nbpResponse.getRates();

        return Collections.max(list, Comparator.comparing(Rate::getMid)).getMid();
    }

    @Override
    public Double findMin(NBPResponse nbpResponse) {
        List<Rate> list = nbpResponse.getRates();

        return Collections.min(list, Comparator.comparing(Rate::getMid)).getMid();
    }

    @Override
    public Double getAvg(NBPResponse nbpResponse) {
        OptionalDouble average = nbpResponse.getRates()
                .stream()
                .mapToDouble(Rate::getMid)
                .average();

        return average.isPresent() ? average.getAsDouble() : 0;
    }

    @Override
    public Double getSD(NBPResponse nbpResponse) {
        List<Rate> list = nbpResponse.getRates();

        Double average = list.stream()
                .mapToDouble(Rate::getMid)
                .average().getAsDouble();

        Double rawSum = list.stream()
                .mapToDouble(x -> Math.pow(x.getMid() - average, 2.0))
                .sum();

        return Math.sqrt(rawSum / (list.size()));
    }

}
