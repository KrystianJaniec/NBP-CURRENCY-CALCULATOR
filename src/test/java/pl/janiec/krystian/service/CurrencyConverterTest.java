package pl.janiec.krystian.service;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;
import static pl.janiec.krystian.util.TestConstants.*;

public class CurrencyConverterTest {

    @InjectMocks
    private CurrencyConverter currencyConverter;

    @Mock
    private CurrencyOperations currencyOperationsMock;

    @Before
    public void setUp() {
        currencyConverter = new CurrencyConverterImpl();

        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldSuccessfullyConvertAmountWithPlnAsTargetCurrency() throws Exception {
        when(currencyOperationsMock.getExchangeRate(TEST_USD_CODE, TEST_DATE_NOW)).thenReturn(2.00);

        Double actualConvertedAmount = currencyConverter.convertAmount(TEST_AMOUNT, TEST_DATE_NOW, TEST_USD_CODE,
                TEST_PLN_CODE);

        Double expectedConvertedAmount = 200.00;

        assertThat(actualConvertedAmount, equalTo(expectedConvertedAmount));
    }

    @Test
    public void shouldSuccessfullyConvertAmountWithPlnAsBaseCurrency() throws Exception {
        when(currencyOperationsMock.getExchangeRate(TEST_USD_CODE, TEST_DATE_NOW)).thenReturn(2.00);

        Double actualConvertedAmount = currencyConverter.convertAmount(TEST_AMOUNT, TEST_DATE_NOW, TEST_PLN_CODE,
                TEST_USD_CODE);

        Double expectedConvertedAmount = 50.00;

        assertThat(actualConvertedAmount, equalTo(expectedConvertedAmount));
    }

    @Test
    public void shouldSuccessfullyConvertAmountWithoutPlnCurrency() throws Exception {
        when(currencyOperationsMock.getExchangeRate(TEST_USD_CODE, TEST_DATE_NOW)).thenReturn(2.00);
        when(currencyOperationsMock.getExchangeRate(TEST_EUR_CODE, TEST_DATE_NOW)).thenReturn(4.00);

        Double actualConvertedAmount = currencyConverter.convertAmount(TEST_AMOUNT, TEST_DATE_NOW, TEST_USD_CODE,
                TEST_EUR_CODE);

        Double expectedConvertedAmount = 50.00;

        assertThat(actualConvertedAmount, equalTo(expectedConvertedAmount));
    }
}
