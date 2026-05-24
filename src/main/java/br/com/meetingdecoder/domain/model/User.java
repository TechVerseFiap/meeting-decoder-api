package br.com.meetingdecoder.domain.model;

import br.com.meetingdecoder.domain.enums.Department;
import br.com.meetingdecoder.domain.shared.validation.DomainValidation;

import java.util.UUID;

public abstract class User {
    protected UUID id;
    protected String firstName;
    protected String lastName;
    protected String email;
    protected Department department;

    protected User(
            String firstName,
            String lastName,
            String email,
            Department department
    ) {
        validate(firstName, lastName, email, department);

        this.id = UUID.randomUUID();
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.department = department;
    }

    private void validate(
            String firstName,
            String lastName,
            String email,
            Department department
    ) {
        DomainValidation.notBlank(firstName, "firstName");
        DomainValidation.notBlank(lastName, "lastName");
        DomainValidation.notBlank(email, "email");
        DomainValidation.notNull(department, "department");
    }

    private String getFullName() {
        return firstName + " " + lastName;
    }
}
