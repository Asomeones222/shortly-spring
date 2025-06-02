package org.pum.shortly.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.pum.shortly.exception.CodeNotFoundException;
import org.pum.shortly.model.ShortURLDTO;
import org.pum.shortly.service.AnalyticsService;
import org.pum.shortly.service.ShortURLService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua_parser.Parser;

@RestController
public class ShortURLController {
    private final Parser uaParser;
    private final ShortURLService shortURLService;
    private final AnalyticsService analyticsService;

    @Autowired
    public ShortURLController(ShortURLService shortURLService,
                              AnalyticsService analyticsService,
                              Parser uaParser
    ) {
        this.shortURLService = shortURLService;
        this.analyticsService = analyticsService;
        this.uaParser = uaParser;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createShortURL(@RequestBody @Valid ShortURLDTO shortURLDTO) {
        var createdShortURL = shortURLService.createShortURL(shortURLDTO.getUrl());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdShortURL);
    }

    @GetMapping("/{code}")
    public ResponseEntity<?> redirect(@PathVariable String code, HttpServletRequest request) {
        var shortURL = shortURLService.getShortURL(code);
        if (shortURL.isEmpty())
            throw new CodeNotFoundException(code);

        var clientUserAgent = new Parser().parse(request.getHeader("user-agent"));
        this.analyticsService.recordClick(shortURL.get(), request.getRemoteAddr(), clientUserAgent, "JO");


        var mappedURL = shortURL.get().getMappedURL();
        var headers = new HttpHeaders();
        headers.set(HttpHeaders.LOCATION, mappedURL);

        return ResponseEntity.status(HttpStatus.FOUND).headers(headers).build();
    }
}
