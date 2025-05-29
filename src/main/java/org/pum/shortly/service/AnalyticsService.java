package org.pum.shortly.service;

import org.pum.shortly.model.ShortURL;
import org.pum.shortly.model.URLClick;
import org.pum.shortly.repository.AnalyticsRepository;
import org.pum.shortly.repository.ShortURLRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AnalyticsService {

    private final AnalyticsRepository urlClickRepository;

    @Autowired
    public AnalyticsService(AnalyticsRepository urlClickRepository, ShortURLRepository shortURLRepository) {
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
