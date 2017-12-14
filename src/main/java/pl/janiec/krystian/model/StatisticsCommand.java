package pl.janiec.krystian.model;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class StatisticsCommand {

    @NotBlank
    private String currency;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "StatisticsCommand{" +
                "currency='" + currency + '\'' +
                ", date=" + date +
                '}';
    }
}
