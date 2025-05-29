package org.pum.shortly.controller;

import jakarta.validation.Valid;
import org.pum.shortly.exception.ResourceNotFoundException;
import org.pum.shortly.model.ShortURLDTO;
import org.pum.shortly.service.AnalyticsService;
import org.pum.shortly.service.ShortURLService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("https://shortly.ziadmrwh.dev")
public class ShortURLController {
    private final ShortURLService shortURLService;
    private final AnalyticsService analyticsService;

    @Autowired
    public ShortURLController(ShortURLService shortURLService,
                              AnalyticsService analyticsService) {
        this.shortURLService = shortURLService;
        this.analyticsService = analyticsService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createShortURL(@RequestBody @Valid ShortURLDTO shortURLRequest) {
        var createdShortURL = shortURLService.createShortURL(shortURLRequest.getUrl());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdShortURL);
    }

    @GetMapping("/{code}")
    public ResponseEntity<?> redirect(@PathVariable String code) {
        var shortURL = shortURLService.getShortURL(code);
        if (shortURL.isEmpty())
            throw new ResourceNotFoundException(code);

        this.analyticsService.recordClick(shortURL.get(), "JO");

        var mappedURL = shortURL.get().getMappedURL();
        var headers = new HttpHeaders();
        headers.set(HttpHeaders.LOCATION, mappedURL);

        return ResponseEntity.status(HttpStatus.FOUND).headers(headers).build();
    }
}
