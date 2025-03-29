package org.pum.shortly.controller;

import org.pum.shortly.service.ShortURLService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ShortURLController {
    final ShortURLService shortURLService;

    @Autowired
    public ShortURLController() {
        shortURLService = new ShortURLService();
    }


    @GetMapping("/{code}")
    public ResponseEntity<?> redirect(@PathVariable String code) {

        var mappedURL = shortURLService.getMappedURL(code);
//        if (mappedURL == null)
//            throw new ResourceNotFoundException(code);
        mappedURL = "https://ziadmrwh.dev";
        var headers = new HttpHeaders();
        headers.set(HttpHeaders.LOCATION, mappedURL);
        return ResponseEntity.status(HttpStatus.FOUND).headers(headers).build();
    }
}
