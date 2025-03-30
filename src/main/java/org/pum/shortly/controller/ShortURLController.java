package org.pum.shortly.controller;

import jakarta.validation.Valid;
import org.pum.shortly.exception.ResourceNotFoundException;
import org.pum.shortly.model.ShortURLRequest;
import org.pum.shortly.service.ShortURLService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ShortURLController {
    private final ShortURLService shortURLService;

    @Autowired
    public ShortURLController(ShortURLService shortURLService) {
        this.shortURLService = shortURLService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createShortURL(@RequestBody @Valid ShortURLRequest shortURLRequest) {
        var createdShortURL = shortURLService.createShortURL(shortURLRequest.getUrl());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdShortURL);
    }

    @GetMapping("/{code}")
    public ResponseEntity<?> redirect(@PathVariable String code) {
        var mappedURL = shortURLService.getMappedURL(code);
        if (mappedURL == null)
            throw new ResourceNotFoundException(code);
        var headers = new HttpHeaders();
        headers.set(HttpHeaders.LOCATION, mappedURL);
        return ResponseEntity.status(HttpStatus.FOUND).headers(headers).build();
    }
}
