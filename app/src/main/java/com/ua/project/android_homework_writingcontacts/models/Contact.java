package com.ua.project.android_homework_writingcontacts.models;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Contact {
    private String firstName;
    private String lastName;
    private String mobilePhoneNumber;
    private String workPhoneNumber;
    private String emailAddress;
    private String birthDate;
}
