package com.replate.backend.dto;

import com.replate.backend.enums.VerificationIDType;
import com.replate.backend.model.User;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class VerificationDto {
    @NotBlank(message = "Please provide an ID Type")
    private VerificationIDType idType;
    @NotBlank(message = "Please provide an ID Number")
    private int idNumber;
    @NotBlank(message = "Please provide a Document Url")
    private String documentUrl;
    private Long userId;
}
