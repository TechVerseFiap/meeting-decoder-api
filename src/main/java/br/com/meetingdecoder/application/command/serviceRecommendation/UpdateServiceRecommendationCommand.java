package br.com.meetingdecoder.application.command.serviceRecommendation;

import br.com.meetingdecoder.domain.enums.ServiceCategory;

import java.math.BigDecimal;

public record UpdateServiceRecommendationCommand(
        String name,
        ServiceCategory category,
        String description,
        BigDecimal price
) {

    public static final class Builder {
        private String name;
        private ServiceCategory category;
        private String description;
        private BigDecimal price;

        public static Builder builder() {
            return new Builder();
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setCategory(ServiceCategory category) {
            this.category = category;
            return this;
        }

        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder setPrice(BigDecimal price) {
            this.price = price;
            return this;
        }

        public UpdateServiceRecommendationCommand build() {
            return new UpdateServiceRecommendationCommand(name, category, description, price);
        }
    }
}
