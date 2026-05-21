package br.com.meetingdecoder.domain.model;

import br.com.meetingdecoder.domain.enums.Department;
import br.com.meetingdecoder.domain.validation.DomainValidation;

import java.util.UUID;

public class Seller extends User {
    private UUID managerId;

    private Seller(
            String firstName,
            String lastName,
            String email,
            Department department,
            UUID managerId
    ) {
        super(firstName, lastName, email, department);
        this.managerId = managerId;
    }

    private void validate(UUID managerId) {
        DomainValidation.notNull(managerId, "managerId");
    }

    public static Seller create(
            String firstName,
            String lastName,
            String email,
            Department department,
            UUID managerId
    ) {
        return new Seller(
                firstName,
                lastName,
                email,
                department,
                managerId
        );
    }
}
