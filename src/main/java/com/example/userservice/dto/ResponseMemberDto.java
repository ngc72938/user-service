package com.example.userservice.dto;

import com.example.userservice.entity.Role;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseMemberDto {
    private long id;
    private String email;
    private String name;
    private String userId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<ResponseOrderDto> orders;
    private List<Role> roles;
}
