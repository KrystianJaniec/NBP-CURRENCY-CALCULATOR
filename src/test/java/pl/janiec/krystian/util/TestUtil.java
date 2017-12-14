package pl.janiec.krystian.util;

import pl.janiec.krystian.model.ConvertCommand;
import pl.janiec.krystian.model.StatisticsCommand;
import pl.janiec.krystian.nbpApi.NBPResponse;
import pl.janiec.krystian.nbpApi.Rate;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;

public class TestUtil {

    public static Rate createRate(String no, LocalDate effectiveDate, double mid) {
        Rate rate = new Rate();
        rate.setNo(no);
        rate.setEffectiveDate(effectiveDate);
        rate.setMid(mid);

        return rate;
    }

    public static ArrayList<Rate> createRateList(Rate... rate) {
        ArrayList<Rate> list = new ArrayList<>();

        list.addAll(Arrays.asList(rate));

        return list;
    }

    public static NBPResponse createNbpResponse(String table, String currencyName, String currencyCode, ArrayList<Rate> rates) {
        NBPResponse nbpResponse = new NBPResponse();
        nbpResponse.setTable(table);
        nbpResponse.setCurrency(currencyName);
        nbpResponse.setCode(currencyCode);
        nbpResponse.setRates(rates);

        return nbpResponse;
    }

    public static String createFormattedDate(LocalDate date) {
        DateTimeFormatter DTF = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        return DTF.format(date);
    }

    public static String setDecimalFormat(Double number) {
        DecimalFormat DF = new DecimalFormat("#.####");

        return DF.format(number);
    }

    public static ConvertCommand createConvertCommand(String baseCurrencyCode, double amount, String targetCurrencyCode, LocalDate date) {
        ConvertCommand convertCommand = new ConvertCommand();
        convertCommand.setBaseCurrency(baseCurrencyCode);
        convertCommand.setAmount(amount);
        convertCommand.setTargetCurrency(targetCurrencyCode);
        convertCommand.setDate(date);

        return convertCommand;
    }

    public static StatisticsCommand createStatisticsCommand(String currencyCode, LocalDate date) {
        StatisticsCommand statisticsCommand = new StatisticsCommand();
        statisticsCommand.setCurrency(currencyCode);
        statisticsCommand.setDate(date);

        return statisticsCommand;
    }
}
