package org.pum.shortly.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;
import org.pum.shortly.dto.ShortURLDTO;
import org.pum.shortly.exception.CodeNotFoundException;
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
    public ResponseEntity<ShortURLDTO> createShortURL(
            @RequestParam(name = "url") @URL @NotBlank String url) {
        var createdShortURLDTO = shortURLService.createShortURL(url).mapToDTO();
        return ResponseEntity.status(HttpStatus.CREATED).body(createdShortURLDTO);
    }

    @GetMapping("/{code}")
    public ResponseEntity<Void> redirect(@PathVariable String code, HttpServletRequest request) {
        var shortURL = shortURLService.getShortURL(code);
        if (shortURL.isEmpty())
            throw new CodeNotFoundException(code);

        var userAgent = request.getHeader(HttpHeaders.USER_AGENT);
        var clientUserAgent = uaParser.parse(userAgent);
        this.analyticsService.recordClick(shortURL.get(), request.getRemoteAddr(), clientUserAgent, "JO");

        var url = shortURL.get().getUrl();
        var headers = new HttpHeaders();
        headers.set(HttpHeaders.LOCATION, url);

        return ResponseEntity.status(HttpStatus.FOUND).headers(headers).build();
    }
}
