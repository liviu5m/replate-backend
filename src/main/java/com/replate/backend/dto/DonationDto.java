package com.replate.backend.dto;

import com.replate.backend.enums.DonationStatus;
import com.replate.backend.enums.QuantityType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class DonationDto {

    @NotBlank(message = "Name is required")
    private String name;
    @NotNull(message = "Quantity is required")
    private double quantity;
    @NotNull(message = "Unit is required")
    private QuantityType unit;
    @NotNull(message = "Expiry Date is required")
    private LocalDate expiryDate;
    private String notes;
    private Long donorId;
    private DonationStatus status;
}
