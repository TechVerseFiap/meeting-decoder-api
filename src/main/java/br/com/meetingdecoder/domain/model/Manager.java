package br.com.meetingdecoder.domain.model;

import br.com.meetingdecoder.domain.enums.Department;

public class Manager extends User {
    private Manager(
            String firstName,
            String lastName,
            String email,
            Department department
    ) {
        super(
                firstName,
                lastName,
                email,
                department
        );
    }

    public static Manager create(
            String firstName,
            String lastName,
            String email,
            Department department
    ) {
        return new Manager(
                firstName,
                lastName,
                email,
                department
        );
    }
}
