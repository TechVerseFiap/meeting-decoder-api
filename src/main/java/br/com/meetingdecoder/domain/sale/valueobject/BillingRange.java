package br.com.meetingdecoder.domain.sale.valueobject;

public class BillingRange {
    private final double minValue;
    private final double maxValue;

    private BillingRange(double minValue, double maxValue) {
        validate(minValue, maxValue);
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    private void validate(double minValue, double maxValue) {
        if (minValue <= 0)
            throw new IllegalArgumentException("Minimum value must be positive");
        if (maxValue <= 0)
            throw new IllegalArgumentException("Maximum value must be positive");
        if (minValue > maxValue)
            throw new IllegalArgumentException("Minimum value cannot be greater than maximum value");
    }

    public static BillingRange of(double minValue, double maxValue) {
        return new BillingRange(minValue, maxValue);
    }

    public double minValue() {
        return minValue;
    }

    public double maxValue() {
        return maxValue;
    }
}
