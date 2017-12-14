package pl.janiec.krystian.service;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pl.janiec.krystian.model.ConvertCommand;
import pl.janiec.krystian.model.ConvertResponse;
import pl.janiec.krystian.model.StatisticsCommand;
import pl.janiec.krystian.model.StatisticsResponse;
import pl.janiec.krystian.nbpApi.NBPResponse;

import java.time.LocalDate;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;
import static pl.janiec.krystian.util.TestConstants.*;
import static pl.janiec.krystian.util.TestUtil.*;

public class CurrencyServiceTest {

    @Mock
    private CurrencyOperations currencyOperations;

    @Mock
    private CurrencyConverter currencyConverter;

    @Mock
    private ConversionOperations conversionOperations;

    @InjectMocks
    private CurrencyService currencyService;

    private ConvertCommand convertCommandMock;
    private StatisticsCommand statisticsCommandMock;

    @Before
    public void setUp() {
        currencyService = new CurrencyServiceImpl();

        currencyOperations = mock(CurrencyOperationsImpl.class);
        convertCommandMock = mock(ConvertCommand.class);
        statisticsCommandMock = mock(StatisticsCommand.class);

        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldConvertAmountWithDataFomConvertCommandAndCreateResponse() throws Exception {
        ConvertCommand convertCommand = createConvertCommand(TEST_PLN_CODE, TEST_AMOUNT, TEST_EUR_CODE, TEST_DATE_NOW);

        when(convertCommandMock.getBaseCurrency()).thenReturn(TEST_PLN_CODE);
        when(convertCommandMock.getAmount()).thenReturn(TEST_AMOUNT);
        when(convertCommandMock.getTargetCurrency()).thenReturn(TEST_EUR_CODE);
        when(convertCommandMock.getDate()).thenReturn(TEST_DATE_NOW);
        when(currencyConverter.convertAmount(convertCommandMock.getAmount(), convertCommandMock.getDate(),
                convertCommandMock.getBaseCurrency(), convertCommandMock.getTargetCurrency()))
                .thenReturn(TEST_CONVERTED_AMOUNT);

        ConvertResponse expectedResponse = new ConvertResponse(setDecimalFormat(TEST_CONVERTED_AMOUNT), TEST_EUR_CODE);
        ConvertResponse actualResponse = currencyService.convert(convertCommand);

        assertThat(actualResponse.getConvertedAmount(), is(equalTo(expectedResponse.getConvertedAmount())));
        assertThat(actualResponse.getTargetCurrencyCode(), is(equalTo(expectedResponse.getTargetCurrencyCode())));
    }

    @Test
    public void shouldCalculateStatisticsWithDataFromStaticsCommandAndCreateResponse() throws Exception {
        StatisticsCommand statisticsCommand = createStatisticsCommand(TEST_EUR_CODE, TEST_DATE);
        NBPResponse nbpResponse = createNbpResponse(TEST_TABLE, TEST_CURRENCY, TEST_EUR_CODE,
                createRateList(createRate(TEST_NUMBER, TEST_DATE, TEST_MID_MAX),
                        createRate(TEST_NUMBER, TEST_DATE, TEST_MID_MIN)));

        when(statisticsCommandMock.getCurrency()).thenReturn(TEST_CURRENCY);
        when(statisticsCommandMock.getDate()).thenReturn(TEST_DATE);
        when(conversionOperations.convertResponse(statisticsCommandMock.getCurrency(),
                statisticsCommandMock.getDate(), LocalDate.now())).thenReturn(nbpResponse);

        when(currencyOperations.findMin(any(NBPResponse.class))).thenReturn(TEST_MID_MIN);
        when(currencyOperations.findMax(any(NBPResponse.class))).thenReturn(TEST_MID_MAX);
        when(currencyOperations.getAvg(any(NBPResponse.class))).thenReturn(TEST_MID_AVG);
        when(currencyOperations.getSD(any(NBPResponse.class))).thenReturn(TEST_MID_SIGMA);

        StatisticsResponse expectedResponse = new StatisticsResponse(setDecimalFormat(TEST_MID_MIN), setDecimalFormat(TEST_MID_MAX),
                setDecimalFormat(TEST_MID_AVG), setDecimalFormat(TEST_MID_SIGMA));
        StatisticsResponse actualResponse = currencyService.calculateStatistics(statisticsCommand);

        assertThat(actualResponse.getMin(), is(equalTo(expectedResponse.getMin())));
        assertThat(actualResponse.getMax(), is(equalTo(expectedResponse.getMax())));
        assertThat(actualResponse.getAvg(), is(equalTo(expectedResponse.getAvg())));
        assertThat(actualResponse.getSD(), is(equalTo(expectedResponse.getSD())));
    }
}
