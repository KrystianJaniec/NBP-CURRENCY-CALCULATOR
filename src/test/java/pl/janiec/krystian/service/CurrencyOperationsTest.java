package pl.janiec.krystian.service;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pl.janiec.krystian.nbpApi.NBPResponse;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;
import static pl.janiec.krystian.util.TestConstants.*;
import static pl.janiec.krystian.util.TestUtil.*;

public class CurrencyOperationsTest {

    @InjectMocks
    private CurrencyOperations currencyOperations;

    @Mock
    private ConversionOperations conversionOperationsMock;

    private NBPResponse nbpResponse;

    @Before
    public void setUp() {
        currencyOperations = new CurrencyOperationsImpl();
        nbpResponse = createNbpResponse(TEST_TABLE, TEST_CURRENCY, TEST_PLN_CODE,
                createRateList(createRate(TEST_NUMBER, TEST_DATE_NOW, TEST_MID_MAX),
                        createRate(TEST_NUMBER, TEST_DATE_NOW, TEST_MID_MIN)));

        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldSuccessfullyGetExchangeRateForGivenCurrency() throws Exception {
        NBPResponse nbpResponse = createNbpResponse(TEST_TABLE, TEST_CURRENCY, TEST_PLN_CODE,
                createRateList(createRate(TEST_NUMBER, TEST_DATE_NOW, TEST_MID)));

        when(conversionOperationsMock.convertResponse(TEST_PLN_CODE, TEST_DATE_NOW)).thenReturn(nbpResponse);

        Double actualRate = currencyOperations.getExchangeRate(TEST_PLN_CODE, TEST_DATE_NOW);

        assertThat(actualRate, equalTo(TEST_MID));
    }

    @Test
    public void shouldSuccessfullyFindMaximumValue() throws Exception {
        when(conversionOperationsMock.convertResponse(TEST_PLN_CODE, TEST_DATE_NOW)).thenReturn(nbpResponse);

        Double actualMax = currencyOperations.findMax(nbpResponse);

        assertThat(actualMax, equalTo(TEST_MID_MAX));
    }

    @Test
    public void shouldSuccessfullyFindMinimumValue() throws Exception {
        when(conversionOperationsMock.convertResponse(TEST_PLN_CODE, TEST_DATE_NOW)).thenReturn(nbpResponse);

        Double actualMin = currencyOperations.findMin(nbpResponse);

        assertThat(actualMin, equalTo(TEST_MID_MIN));
    }

    @Test
    public void shouldSuccessfullyCalculateAverageOfCurrencyValues() throws Exception {
        when(conversionOperationsMock.convertResponse(TEST_PLN_CODE, TEST_DATE_NOW)).thenReturn(nbpResponse);

        Double actualAvg = currencyOperations.getAvg(nbpResponse);

        assertThat(actualAvg, equalTo(TEST_MID_AVG));
    }

    @Test
    public void shouldSuccessfullyCalculateStandardDeviationOfCurrencyValues() throws Exception {
        when(conversionOperationsMock.convertResponse(TEST_PLN_CODE, TEST_DATE_NOW)).thenReturn(nbpResponse);

        Double actualSD = currencyOperations.getSD(nbpResponse);

        assertThat(actualSD, equalTo(TEST_MID_SIGMA));
    }
}
