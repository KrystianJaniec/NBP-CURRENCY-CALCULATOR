package pl.janiec.krystian.model;

public class ConvertResponse {

    private final String convertedAmount;
    private final String targetCurrencyCode;

    public ConvertResponse(String convertedAmount, String targetCurrencyCode) {
        this.convertedAmount = convertedAmount;
        this.targetCurrencyCode = targetCurrencyCode;
    }

    public String getConvertedAmount() {
        return convertedAmount;
    }

    public String getTargetCurrencyCode() {
        return targetCurrencyCode;
    }
}
