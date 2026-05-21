package br.com.meetingdecoder.domain.model;

import br.com.meetingdecoder.domain.enums.ClientIndustry;
import br.com.meetingdecoder.domain.enums.CompanySize;
import br.com.meetingdecoder.domain.enums.CurrentSystem;
import br.com.meetingdecoder.domain.validation.DomainValidation;

import java.util.UUID;

public class Client {
    private UUID id;
    private String companyName;
    private String representativeName;
    private ClientIndustry industry;
    private CompanySize companySize;
    private CurrentSystem currentSystem;

    private Client(
            String companyName,
            String representativeName,
            ClientIndustry industry,
            CompanySize companySize,
            CurrentSystem currentSystem
    ) {
        validate(companyName, representativeName, industry, companySize, currentSystem);

        this.id = UUID.randomUUID();
        this.companyName = companyName;
        this.representativeName = representativeName;
        this.industry = industry;
        this.companySize = companySize;
        this.currentSystem = currentSystem;
    }

    private void validate(
            String companyName,
            String representativeName,
            ClientIndustry industry,
            CompanySize companySize,
            CurrentSystem currentSystem
    ) {
        DomainValidation.notBlank(companyName, "companyName");
        DomainValidation.notBlank(representativeName, "representativeName");
        DomainValidation.notNull(industry, "industry");
        DomainValidation.notNull(companySize, "companySize");
        DomainValidation.notNull(currentSystem, "currentSystem");
    }

    public static Client create(
            String companyName,
            String representativeName,
            ClientIndustry industry,
            CompanySize companySize,
            CurrentSystem currentSystem
    ) {
        return new Client(companyName, representativeName, industry, companySize, currentSystem);
    }
}
