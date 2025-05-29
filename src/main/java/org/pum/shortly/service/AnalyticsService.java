package org.pum.shortly.service;

import org.pum.shortly.model.ShortURL;
import org.pum.shortly.model.URLClick;
import org.pum.shortly.repository.ShortURLRepository;
import org.pum.shortly.repository.URLClickRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AnalyticsService {

    private final URLClickRepository urlClickRepository;

    @Autowired
    public AnalyticsService(URLClickRepository urlClickRepository, ShortURLRepository shortURLRepository) {
        this.urlClickRepository = urlClickRepository;
    }

    public void recordClick(ShortURL mapping, String country) {
        URLClick click = URLClick.builder()
                .shortURL(mapping)
                .country(country)
                .localDateTime(LocalDateTime.now())
                .build();
        urlClickRepository.save(click);
    }
}
