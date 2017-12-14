package pl.janiec.krystian.service;

import pl.janiec.krystian.model.ConvertCommand;
import pl.janiec.krystian.model.ConvertResponse;
import pl.janiec.krystian.model.StatisticsCommand;
import pl.janiec.krystian.model.StatisticsResponse;
import pl.janiec.krystian.nbpApi.NBPResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.time.LocalDate;

@Service
public class CurrencyServiceImpl implements CurrencyService {

    private static final DecimalFormat DF = new DecimalFormat("#.####");

    @Autowired
    private CurrencyOperations currencyOperations;

    @Autowired
    private CurrencyConverter currencyConverter;

    @Autowired
    private ConversionOperations conversionOperations;


    public ConvertResponse convert(ConvertCommand convertCommand) {
        String convertedAmount = DF.format(currencyConverter.convertAmount(convertCommand.getAmount(), convertCommand.getDate(),
                convertCommand.getBaseCurrency(), convertCommand.getTargetCurrency()));

        return new ConvertResponse(convertedAmount, convertCommand.getTargetCurrency());
    }


    public StatisticsResponse calculateStatistics(StatisticsCommand statisticsCommand) {
        NBPResponse nbpResponse = conversionOperations.convertResponse(statisticsCommand.getCurrency(),
                statisticsCommand.getDate(), LocalDate.now());

        String Min = DF.format(currencyOperations.findMin(nbpResponse));
        String Max = DF.format(currencyOperations.findMax(nbpResponse));
        String Avg = DF.format(currencyOperations.getAvg(nbpResponse));
        String SD = DF.format(currencyOperations.getSD(nbpResponse));

        return new StatisticsResponse(Min, Max, Avg, SD);
    }

}
