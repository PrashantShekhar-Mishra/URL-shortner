package com.example.url_shortner.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ShortenUrlRequest {

    @NotBlank
    private String url;

    private String customCode;
}