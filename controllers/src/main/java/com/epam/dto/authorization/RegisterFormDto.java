package com.epam.dto.authorization;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RegisterFormDto {
    private String username;
    private String password;
    private String repeatPassword;
    private String email;
    private String firstName;
    private String lastName;
}
