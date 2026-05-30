package br.com.meetingdecoder.domain.sale.valueobject;

import br.com.meetingdecoder.domain.sale.enums.NpsCategory;
import br.com.meetingdecoder.domain.shared.validation.DomainValidation;

import java.time.LocalDateTime;

public class NpsSnapshot {
    private static final Double LOW_NPS = 5.00;
    private static final Double HIGH_NPS = 9.00;

    private final Double npsNote;
    private final LocalDateTime npsDate;
    private final NpsCategory category;

    private NpsSnapshot(Double npsNote, LocalDateTime npsDate) {
        validate(npsNote, npsDate);
        this.npsNote = npsNote;
        this.npsDate = npsDate;
        this.category = getCategory();
    }

    private void validate(Double npsNote, LocalDateTime npsDate) {
        DomainValidation.notNull(npsNote, "npsNote");
        DomainValidation.notFuture(npsDate, "npsDate");
        if (npsNote < 0 || npsNote > 10)
            throw new IllegalArgumentException("The nps value must be between 0 and 10: " + npsNote);
    }

    private NpsCategory getCategory() {
        if (npsNote <= LOW_NPS)
            return NpsCategory.LOW;
        if (npsNote >= HIGH_NPS)
            return NpsCategory.HIGH;
        return NpsCategory.MEDIUM;
    }

    public static NpsSnapshot of(Double npsNote, LocalDateTime npsDate) {
        return new NpsSnapshot(npsNote, npsDate);
    }

    public double npsNote() {
        return npsNote;
    }

    public LocalDateTime npsDate() {
        return npsDate;
    }

    public NpsCategory category() {
        return category;
    }
}
