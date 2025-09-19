package com.replate.backend.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileDataDto {

    @NotBlank(message = "Full name is required")
    @Size(min = 2, max = 100, message = "Full name must be between 2 and 100 characters")
    private String fullName;

    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    private String username;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^(\\+62|0)(21|821)\\d{7,9}$",
            message = "Please provide a valid phone number. Valid formats: +6221XXXXXXX, 021XXXXXXX, 0821XXXXXXX")
    private String phone;

    @Pattern(regexp = "^(|.{5,200})$", message = "Address must be between 5 and 200 characters if provided")
    private String address;

    @NotBlank(message = "Role is required")
    private String role;

    private String image;

    @Pattern(regexp = "^(|.{2,50})$", message = "City must be between 2 and 50 characters if provided")
    private String city;

    @Pattern(regexp = "^(|^[0-9]{5}(-[0-9]{4})?$)$", message = "Please enter a valid ZIP code if provided")
    private String zip;

    @Pattern(regexp = "^(|.{2,50})$", message = "Country must be between 2 and 50 characters if provided")
    private String country;

    @Pattern(regexp = "^(|.{8,})$", message = "Current password must be at least 8 characters if provided")
    private String currentPassword;

    @Pattern(regexp = "^(|.{8,})$", message = "New password must be at least 8 characters if provided")
    private String newPassword;

    @Pattern(regexp = "^(|.{8,})$", message = "Password confirmation must be at least 8 characters if provided")
    private String passwordConfirmation;
    private String updateType;
}