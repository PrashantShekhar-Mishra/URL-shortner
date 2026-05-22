package com.example.url_shortner.controller;

import com.example.url_shortner.dto.ShortenUrlRequest;
import com.example.url_shortner.dto.ShortenUrlResponse;
import com.example.url_shortner.service.UrlShortenerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequiredArgsConstructor
public class UrlShortenerController {

    private final UrlShortenerService service;

    @PostMapping("/api/v1/shorten")
    public ResponseEntity<ShortenUrlResponse> shorten(
            @Valid @RequestBody ShortenUrlRequest request
    ) {
        return ResponseEntity.ok(service.shortenUrl(request));
    }
    @GetMapping("/{shortCode}")
    public ResponseEntity<Void> redirect(
            @PathVariable String shortCode
    ) {

        String originalUrl = service.getOriginalUrl(shortCode);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(originalUrl));

        return ResponseEntity.status(302).headers(headers).build();
    }
}
