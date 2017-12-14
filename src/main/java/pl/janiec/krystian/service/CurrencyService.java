package pl.janiec.krystian.service;

import pl.janiec.krystian.model.ConvertCommand;
import pl.janiec.krystian.model.ConvertResponse;
import pl.janiec.krystian.model.StatisticsCommand;
import pl.janiec.krystian.model.StatisticsResponse;

public interface CurrencyService {

    ConvertResponse convert(ConvertCommand convertCommand);

    StatisticsResponse calculateStatistics(StatisticsCommand statisticsCommand);
}
