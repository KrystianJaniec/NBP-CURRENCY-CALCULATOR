package pl.janiec.krystian.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import pl.janiec.krystian.service.CurrencyOperations;

@Controller
public class HomeController {

    private final CurrencyOperations currencyOperations;

    @Autowired
    public HomeController(CurrencyOperations currencyOperations) {
        this.currencyOperations = currencyOperations;
    }

    @GetMapping("/")
    public String home(Model model) throws HttpClientErrorException {
        try {
            Double USD = currencyOperations.getExchangeRate("USD", LocalDate.now());
            Double EUR = currencyOperations.getExchangeRate("EUR", LocalDate.now());
            Double GBP = currencyOperations.getExchangeRate("GBP", LocalDate.now());

            model.addAttribute("rateUSD", USD);
            model.addAttribute("rateEUR", EUR);
            model.addAttribute("rateGBP", GBP);
        } catch (RestClientException e) {
            e.getMessage();
        } finally {
            model.addAttribute("view", "home");
        }
        return "layout";
    }


}
