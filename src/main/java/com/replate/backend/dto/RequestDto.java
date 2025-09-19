package com.replate.backend.dto;

import com.replate.backend.enums.RequestStatus;
import lombok.Getter;

import java.util.Date;

@Getter
public class RequestDto {
    private Long ngoId;
    private RequestStatus status;
    private Date pickupDate;
    private Date deliveryDate;
    private Long driverId;
}
