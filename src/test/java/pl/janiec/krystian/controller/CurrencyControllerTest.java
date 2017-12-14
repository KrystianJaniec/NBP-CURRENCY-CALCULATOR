package pl.janiec.krystian.controller;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import pl.janiec.krystian.model.ConvertCommand;
import pl.janiec.krystian.model.ConvertResponse;
import pl.janiec.krystian.service.CurrencyService;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static pl.janiec.krystian.util.TestConstants.*;
import static pl.janiec.krystian.util.TestUtil.*;

public class CurrencyControllerTest {

    @Mock
    private CurrencyService currencyService;

    @InjectMocks
    private CurrencyController currencyController;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        currencyController = new CurrencyController(currencyService);

        mockMvc = MockMvcBuilders.standaloneSetup(currencyController).build();
    }

    @Test
    public void shouldShowConvertForm() throws Exception {
        mockMvc.perform(get("/convert"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("view", "forms/convert"))
                .andExpect(view().name("layout"));
    }

    @Test
    public void shouldAddConvertedAmountOnViewWhenConvertedAmountValueIsNotNull() throws Exception {
        ConvertCommand convertCommand = createConvertCommand(TEST_PLN_CODE, TEST_AMOUNT, TEST_EUR_CODE, TEST_DATE_NOW);
        ConvertResponse convertResponse = new ConvertResponse(setDecimalFormat(TEST_CONVERTED_AMOUNT), TEST_EUR_CODE);

        when(currencyService.convert(any(ConvertCommand.class))).thenReturn(convertResponse);

        mockMvc.perform(post("/convert")
                .param("baseCurrency", TEST_PLN_CODE)
                .param("amount", setDecimalFormat(TEST_AMOUNT))
                .param("targetCurrency", TEST_EUR_CODE)
                .param("date", createFormattedDate(TEST_DATE))
                .sessionAttr("convertData", convertCommand)
        )
                .andExpect(status().isOk())
                .andExpect(model().attribute("convertedAmount", setDecimalFormat(TEST_CONVERTED_AMOUNT)))
                .andExpect(model().attribute("view", "forms/convert"))
                .andExpect(view().name("layout"));
    }

    @Test
    public void shouldSetConversionFailedAsTrueWhenConvertedAmountIsNull() throws Exception {
        ConvertCommand convertCommand = createConvertCommand(TEST_PLN_CODE, TEST_AMOUNT, TEST_EUR_CODE, TEST_DATE_NOW);
        ConvertResponse convertResponse = new ConvertResponse(null, TEST_EUR_CODE);

        when(currencyService.convert(any(ConvertCommand.class))).thenReturn(convertResponse);

        mockMvc.perform(post("/convert")
                .param("baseCurrency", TEST_PLN_CODE)
                .param("amount", setDecimalFormat(TEST_AMOUNT))
                .param("targetCurrency", TEST_EUR_CODE)
                .param("date", createFormattedDate(TEST_DATE))
                .sessionAttr("convertData", convertCommand)
        )
                .andExpect(status().isOk())
                .andExpect(model().attribute("conversionFailed", true))
                .andExpect(model().attribute("view", "forms/convert"))
                .andExpect(view().name("layout"));
    }
}
