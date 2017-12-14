package pl.janiec.krystian.controller;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.ModelAttribute;
import pl.janiec.krystian.model.ConvertResponse;
import pl.janiec.krystian.service.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import pl.janiec.krystian.model.ConvertCommand;

@Controller
public class CurrencyController {

    private final CurrencyService currencyService;

    @Autowired
    public CurrencyController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @GetMapping("/convert")
    public String convert(Model model) {
        model.addAttribute("convertData", new ConvertCommand());
        model.addAttribute("view", "forms/convert");

        return "layout";
    }

    @PostMapping("/convert")
    public String convertProcess(@ModelAttribute("convertData") @Valid ConvertCommand convertCommand, Model model) {
        ConvertResponse convertResponse = currencyService.convert(convertCommand);
        String convertedAmount = convertResponse.getConvertedAmount();

        if (convertedAmount != null) {
            model.addAttribute("convertedAmount", convertedAmount);
        } else {
            model.addAttribute("conversionFailed", true);
        }
        model.addAttribute("view", "forms/convert");

        return "layout";
    }

}
