package br.com.meetingdecoder.application.command.seller;

import br.com.meetingdecoder.domain.sale.enums.SellerType;
import br.com.meetingdecoder.domain.sale.valueobject.Email;
import br.com.meetingdecoder.domain.sale.valueobject.SellerId;

public record UpdateSellerCommand(
        SellerId managerId,
        SellerType type,
        String name,
        Email email
) {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private SellerId managerId;
        private SellerType type;
        private String name;
        private Email email;

        public Builder managerId(SellerId value) {
            this.managerId = value;
            return this;
        }

        public Builder type(SellerType value) {
            this.type = value;
            return this;
        }

        public Builder name(String value) {
            this.name = value;
            return this;
        }

        public Builder email(Email value) {
            this.email = value;
            return this;
        }

        public UpdateSellerCommand build() {
            return new UpdateSellerCommand(
                    managerId,
                    type,
                    name,
                    email
            );
        }
    }
}
