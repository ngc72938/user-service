package com.example.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateMemberDTO {
    @NotNull(message = "Email cannot be null")
    @Size(min = 2, message="Email not be less than two characters")
    @Email
    private String email;

    @NotNull(message = "Name cannot be null")
    @Size(min = 2, message="Name not be less than two characters")
    private String name;

    @NotNull(message = "Password cannot be null")
    @Size(min = 8, message = "password must be equal or grater than 8 characters")
    private String password;

    @NotNull(message = "userId cannot be null")
    @Size(min = 5)
    private String userId;
}
