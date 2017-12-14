package pl.janiec.krystian.controller;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import pl.janiec.krystian.model.StatisticsCommand;
import pl.janiec.krystian.model.StatisticsResponse;
import pl.janiec.krystian.service.CurrencyService;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static pl.janiec.krystian.util.TestConstants.*;
import static pl.janiec.krystian.util.TestUtil.*;

public class StatisticControllerTest {

    @Mock
    private CurrencyService currencyService;

    @InjectMocks
    private StatisticsController statisticsController;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        statisticsController = new StatisticsController(currencyService);

        mockMvc = MockMvcBuilders.standaloneSetup(statisticsController).build();
    }

    @Test
    public void shouldShowCurrencyStatisticsForm() throws Exception {
        mockMvc.perform(get("/statistics"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("view", "forms/statistics"))
                .andExpect(view().name("layout"));
    }

    @Test
    public void shouldCalculateAllStatisticsForGivenCurrencyAndAddDataToModel() throws Exception {
        StatisticsCommand statisticsCommand = createStatisticsCommand(TEST_EUR_CODE, TEST_DATE_NOW);
        StatisticsResponse statisticsResponse = new StatisticsResponse(setDecimalFormat(TEST_MID_MIN), setDecimalFormat(TEST_MID_MAX),
                setDecimalFormat(TEST_MID_AVG), setDecimalFormat(TEST_MID_SIGMA));

        when(currencyService.calculateStatistics(any(StatisticsCommand.class))).thenReturn(statisticsResponse);

        mockMvc.perform(post("/statistics")
                .param("currency", TEST_EUR_CODE)
                .param("date", createFormattedDate(TEST_DATE_NOW))
                .sessionAttr("statisticsData", statisticsCommand)
        )
                .andExpect(status().isOk())
                .andExpect(model().attribute("MIN", setDecimalFormat(TEST_MID_MIN)))
                .andExpect(model().attribute("MAX", setDecimalFormat(TEST_MID_MAX)))
                .andExpect(model().attribute("AVG", setDecimalFormat(TEST_MID_AVG)))
                .andExpect(model().attribute("SD", setDecimalFormat(TEST_MID_SIGMA)))
                .andExpect(model().attribute("view", "forms/statistics"))
                .andExpect(view().name("layout"));
    }
}
