package com.example.userservice.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ResponseOrderDto {
    private String productId;
    private Integer quantity;
    private Integer unitPrice;
    private Integer totalPrice;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String orderId;
}
