package pl.janiec.krystian.controller;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.ModelAttribute;
import pl.janiec.krystian.model.StatisticsCommand;
import pl.janiec.krystian.model.StatisticsResponse;
import pl.janiec.krystian.service.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class StatisticsController {

    private final CurrencyService currencyService;

    @Autowired
    public StatisticsController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @GetMapping("/statistics")
    public String statistics(Model model) {
        model.addAttribute("statisticsData", new StatisticsCommand());
        model.addAttribute("view", "forms/statistics");

        return "layout";
    }

    @PostMapping("/statistics")
    public String statisticsProcess(@ModelAttribute("statisticsData") @Valid StatisticsCommand statisticsCommand, Model model) {
        StatisticsResponse statisticsResponse = currencyService.calculateStatistics(statisticsCommand);

        String min = statisticsResponse.getMin();
        String max = statisticsResponse.getMax();
        String avg = statisticsResponse.getAvg();
        String sd = statisticsResponse.getSD();

        model.addAttribute("MIN", min);
        model.addAttribute("MAX", max);
        model.addAttribute("AVG", avg);
        model.addAttribute("SD", sd);

        model.addAttribute("view", "forms/statistics");

        return "layout";
    }
}
