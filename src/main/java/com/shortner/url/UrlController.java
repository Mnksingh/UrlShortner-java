package com.shortner.url;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
public class UrlController {

    @Autowired
    private UrlService service;

    @PostMapping("/shorten")
    public String shortenUrl(@RequestBody Map<String, String> request) {
        String originalUrl = request.get("url");
        String shortCode = service.createShortUrl(originalUrl);
        return "http://localhost:8080/r/" + shortCode;
    }

    @GetMapping("/r/{code}")
    public ResponseEntity<Void> redirect(@PathVariable String code) {
        String originalUrl = service.getOriginalUrl(code);
        if (originalUrl == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(302)
                .location(URI.create(originalUrl))
                .build();
    }
}