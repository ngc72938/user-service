package com.example.userservice.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ResponseMemberDTO {
    private String email;
    private String name;
    private String userId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
