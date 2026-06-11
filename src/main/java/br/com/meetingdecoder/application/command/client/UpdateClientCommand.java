package br.com.meetingdecoder.application.command.client;

import br.com.meetingdecoder.domain.sale.enums.ClientSize;
import br.com.meetingdecoder.domain.sale.enums.ClientType;
import br.com.meetingdecoder.domain.sale.valueobject.BillingRange;
import br.com.meetingdecoder.domain.sale.valueobject.NpsSnapshot;


public record UpdateClientCommand(
        String corporateReason,
        String fantasyName,
        String cnpj,
        String cnae,
        String segment,
        ClientSize size,
        String city,
        String state,
        String country,
        ClientType type,
        BillingRange billingRange,
        NpsSnapshot npsSnapshot
) {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String corporateReason;
        private String fantasyName;
        private String cnpj;
        private String cnae;
        private String segment;
        private ClientSize size;
        private String city;
        private String state;
        private String country;
        private ClientType type;
        private BillingRange billingRange;
        private NpsSnapshot npsSnapshot;

        public Builder corporateReason(String value) {
            this.corporateReason = value;
            return this;
        }

        public Builder fantasyName(String value) {
            this.fantasyName = value;
            return this;
        }

        public Builder cnpj(String value) {
            this.cnpj = value;
            return this;
        }

        public Builder cnae(String value) {
            this.cnae = value;
            return this;
        }

        public Builder segment(String value) {
            this.segment = value;
            return this;
        }

        public Builder size(ClientSize value) {
            this.size = value;
            return this;
        }

        public Builder city(String value) {
            this.city = value;
            return this;
        }

        public Builder state(String value) {
            this.state = value;
            return this;
        }

        public Builder country(String value) {
            this.country = value;
            return this;
        }

        public Builder type(ClientType value) {
            this.type = value;
            return this;
        }

        public Builder billingRange(BillingRange value) {
            this.billingRange = value;
            return this;
        }

        public Builder npsSnapshot(NpsSnapshot value) {
            this.npsSnapshot = value;
            return this;
        }

        public UpdateClientCommand build() {
            return new UpdateClientCommand(
                    corporateReason,
                    fantasyName,
                    cnpj,
                    cnae,
                    segment,
                    size,
                    city,
                    state,
                    country,
                    type,
                    billingRange,
                    npsSnapshot
            );
        }
    }
}
