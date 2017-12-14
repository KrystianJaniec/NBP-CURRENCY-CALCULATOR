package pl.janiec.krystian.controller;


import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import pl.janiec.krystian.service.CurrencyOperations;
import java.time.LocalDate;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static pl.janiec.krystian.util.TestConstants.*;
import static pl.janiec.krystian.util.TestUtil.*;


public class HomeControllerTest {

    @Mock
    private CurrencyOperations currencyOperations;

    @InjectMocks
    private HomeController homeController;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        homeController = new HomeController(currencyOperations);

        mockMvc = MockMvcBuilders.standaloneSetup(homeController).build();
    }

    @Test
    public void shouldAddToModelExchangeRatesWhenCurrentDataIsAvailable()throws Exception{
        when(currencyOperations.getExchangeRate(TEST_EUR_CODE,LocalDate.now())).thenReturn(TEST_MID);
        when(currencyOperations.getExchangeRate(TEST_USD_CODE,LocalDate.now())).thenReturn(TEST_MID_MAX);
        when(currencyOperations.getExchangeRate(TEST_GBP_CODE,LocalDate.now())).thenReturn(TEST_MID_MIN);

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("rateEUR",TEST_MID))
                .andExpect(model().attribute("rateUSD",TEST_MID_MAX))
                .andExpect(model().attribute("rateGBP",TEST_MID_MIN))
                .andExpect(model().attribute("view", "home"))
                .andExpect(view().name("layout"));
    }
}
