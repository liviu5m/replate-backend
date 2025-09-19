package com.replate.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRoleDto {

    @NotBlank(message = "You must select a role")
    private String role;
    @NotBlank(message = "You're address must be valid")
    private String address;
    @NotBlank(message = "You need to pass a phone number")
    @Pattern(regexp = "^(\\+62|0)(21|821)\\d{7,9}$",
            message = "Please provide a valid Jakarta phone number. Valid formats: +6221XXXXXXX, 021XXXXXXX, 0821XXXXXXX")
    private String phone;
    private Long userId;
    private String type;

}
