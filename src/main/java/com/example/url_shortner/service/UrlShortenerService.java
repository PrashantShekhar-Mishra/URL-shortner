package com.example.url_shortner.service;

import com.example.url_shortner.dto.ShortenUrlRequest;
import com.example.url_shortner.dto.ShortenUrlResponse;
import com.example.url_shortner.entity.ShortUrl;
import com.example.url_shortner.repository.ShortUrlRepository;
import com.example.url_shortner.utility.Base62Encoder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UrlShortenerService {

    private final ShortUrlRepository repository;
    private final RedisTemplate<String, String> redisTemplate;

    public ShortenUrlResponse shortenUrl(ShortenUrlRequest request) {

        String shortCode;

        if (request.getCustomCode() != null && !request.getCustomCode().isBlank()) {
            shortCode = request.getCustomCode();
        } else {
            shortCode = generateShortCode();
        }

        ShortUrl shortUrl = ShortUrl.builder()
                .originalUrl(request.getUrl())
                .shortCode(shortCode)
                .clickCount(0L)
                .createdAt(LocalDateTime.now())
                .build();

        repository.save(shortUrl);

        return ShortenUrlResponse.builder()
                .originalUrl(request.getUrl())
                .shortUrl("http://localhost:8080/" + shortCode)
                .build();
    }

    public String getOriginalUrl(String shortCode) {

        String cached = redisTemplate.opsForValue().get(shortCode);

        if (cached != null) {
            return cached;
        }

        ShortUrl shortUrl = repository.findByShortCode(shortCode)
                .orElseThrow(() -> new RuntimeException("Short URL not found"));

        shortUrl.setClickCount(shortUrl.getClickCount() + 1);
        repository.save(shortUrl);

        redisTemplate.opsForValue()
                .set(shortCode, shortUrl.getOriginalUrl(), Duration.ofHours(1));

        return shortUrl.getOriginalUrl();
    }

        private String generateShortCode() {
            long id = System.currentTimeMillis();
            return Base62Encoder.encode(id);
        }
    }
