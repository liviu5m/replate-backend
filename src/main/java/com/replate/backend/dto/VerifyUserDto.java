package com.replate.backend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VerifyUserDto {

    private Long userId;
    private String code;

}
