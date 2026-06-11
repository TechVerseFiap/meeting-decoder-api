package br.com.meetingdecoder.domain.sale.valueobject;

import br.com.meetingdecoder.domain.shared.validation.DomainError;
import br.com.meetingdecoder.domain.shared.validation.DomainErrorCode;
import br.com.meetingdecoder.domain.shared.validation.ErrorCollector;

public class BillingRange {
    private final double minValue;
    private final double maxValue;

    private BillingRange(double minValue, double maxValue) {
        validate(minValue, maxValue);
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    private void validate(
            double minValue,
            double maxValue
    ) {
        ErrorCollector.builder()
                .check(
                        minValue > 0,
                        DomainError.of(
                                DomainErrorCode.INVALID_BILLING_RANGE,
                                "minValue"
                        )
                )
                .check(
                        maxValue > 0,
                        DomainError.of(
                                DomainErrorCode.INVALID_BILLING_RANGE,
                                "maxValue"
                        )
                )
                .check(
                        minValue <= maxValue,
                        DomainError.of(
                                DomainErrorCode.INVALID_BILLING_RANGE,
                                "billingRange"
                        )
                )
                .validate();
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
