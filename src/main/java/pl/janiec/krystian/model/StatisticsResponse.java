package pl.janiec.krystian.model;

public class StatisticsResponse {

    private final String Min;
    private final String Max;
    private final String Avg;
    private final String SD;

    public StatisticsResponse(String Min, String Max, String Avg, String SD) {
        this.Min = Min;
        this.Max = Max;
        this.Avg = Avg;
        this.SD = SD;
    }

    public String getMin() {
        return Min;
    }

    public String getMax() {
        return Max;
    }

    public String getAvg() {
        return Avg;
    }

    public String getSD() {
        return SD;
    }
}
