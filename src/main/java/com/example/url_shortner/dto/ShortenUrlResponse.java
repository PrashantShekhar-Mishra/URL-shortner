package com.example.url_shortner.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ShortenUrlResponse {

    private String originalUrl;
    private String shortUrl;
}