package pl.janiec.krystian.service;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;
import pl.janiec.krystian.nbpApi.NBPResponse;
import pl.janiec.krystian.util.TestUtil;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;
import static pl.janiec.krystian.util.TestConstants.*;
import static pl.janiec.krystian.util.TestUtil.*;

public class ConversionOperationsTest {

    private NBPResponse nbpResponse;

    @InjectMocks
    private ConversionOperations conversionOperations;

    @Mock
    private RestTemplate restTemplateMock;

    @Before
    public void setUp() {
        conversionOperations = new ConversionOperationsImpl();
        nbpResponse = TestUtil.createNbpResponse(TEST_TABLE, TEST_CURRENCY, TEST_PLN_CODE,
                TestUtil.createRateList(TestUtil.createRate(TEST_NUMBER, TEST_DATE_NOW, TEST_MID)));

        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldSuccessfullyConvertResponseWithGivenCurrencyCodeAndDate() throws Exception {
        when(restTemplateMock.getForObject(URL_DATE, NBPResponse.class, TEST_PLN_CODE, createFormattedDate(TEST_DATE_NOW)))
                .thenReturn(nbpResponse);

        NBPResponse actualResponse = conversionOperations.convertResponse(TEST_PLN_CODE, TEST_DATE_NOW);
        NBPResponse expectedResponse = nbpResponse;

        assertThat(actualResponse, equalTo(expectedResponse));
    }

    @Test
    public void shouldSuccessfullyConvertResponseWithGivenCurrencyCodeAndDateAndPastDays() throws Exception {
        when(restTemplateMock.getForObject(URL_BETWEEN_DATES, NBPResponse.class, TEST_PLN_CODE, createFormattedDate(TEST_DATE),
                createFormattedDate(TEST_DATE_NOW))).thenReturn(nbpResponse);

        NBPResponse actualResponse = conversionOperations.convertResponse(TEST_PLN_CODE, TEST_DATE, TEST_DATE_NOW);
        NBPResponse expectedResponse = nbpResponse;

        assertThat(actualResponse, equalTo(expectedResponse));
    }
}
