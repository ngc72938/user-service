package com.example.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
public class ResponseDto {
    private String message;
    private Object responseData;

    public Map<String, Object> getResponseEntity() {
        HashMap<String, Object> responseMap = new HashMap<>();
        HashMap<String, Object> payload = new HashMap<>();

        payload.put("message", this.message);
        payload.put("data", responseData);

        responseMap.put("payload", payload);

        return responseMap;
    }
}
