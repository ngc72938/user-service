package com.example.userservice.exception;

import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

@Service
public class MyErrorAttributes extends DefaultErrorAttributes {
    @Override
    public Map<String, Object> getErrorAttributes(WebRequest webRequest, boolean includeStackTrace) {
        return super.getErrorAttributes(webRequest, includeStackTrace);
    }
}
