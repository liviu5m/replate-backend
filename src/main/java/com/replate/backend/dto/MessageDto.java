package com.replate.backend.dto;

import lombok.Getter;

import java.time.Instant;

@Getter
public class MessageDto {
    private Long senderId;
    private Long receiverId;
    private String text;
}
