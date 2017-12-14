package pl.janiec.krystian.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class ConvertCommand {

    @NotBlank
    private String baseCurrency;

    @NotNull
    @Min(0)
    private double amount;

    @NotBlank
    private String targetCurrency;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    public String getBaseCurrency() {
        return baseCurrency;
    }

    public void setBaseCurrency(String baseCurrency) {
        this.baseCurrency = baseCurrency;
    }

    public String getTargetCurrency() {
        return targetCurrency;
    }

    public void setTargetCurrency(String targetCurrency) {
        this.targetCurrency = targetCurrency;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "ConvertCommand{" +
                "baseCurrency='" + baseCurrency + '\'' +
                ", amount=" + amount +
                ", targetCurrency='" + targetCurrency + '\'' +
                ", date=" + date +
                '}';
    }
}
