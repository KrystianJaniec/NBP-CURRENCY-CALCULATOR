package pl.janiec.krystian.util;

import java.time.LocalDate;

public final class TestConstants {

    public static final String TEST_CURRENCY = "złoty";
    public static final String TEST_PLN_CODE = "PLN";
    public static final String TEST_USD_CODE = "USD";
    public static final String TEST_EUR_CODE = "EUR";
    public static final String TEST_GBP_CODE = "GBP";
    public static final LocalDate TEST_DATE_NOW = LocalDate.now();
    public static final LocalDate TEST_DATE = LocalDate.now().minusDays(4);
    public static final String TEST_TABLE = "A";
    public static final String TEST_NUMBER = "123/A/NBP/2017";
    public static final Double TEST_MID = 1.23;
    public static final Double TEST_MID_MAX = 3.33;
    public static final Double TEST_MID_MIN = 1.11;
    public static final Double TEST_MID_AVG = 2.22;
    public static final Double TEST_MID_SIGMA = 1.11;
    public static final Double TEST_AMOUNT = 100.00;
    public static final Double TEST_CONVERTED_AMOUNT = 12.3456;
    public static final String URL_DATE = "http://api.nbp.pl/api/exchangerates/rates/A/{code}/{date}/?format=json";
    public static final String URL_BETWEEN_DATES = "http://api.nbp.pl/api/exchangerates/rates/A/{code}/{startDate}/{endDate}/?format=json";

}
