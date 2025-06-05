package org.pum.shortly.service;

import org.pum.shortly.model.AnalyticsRecord;
import org.pum.shortly.model.ShortURL;
import org.pum.shortly.repository.AnalyticsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua_parser.Client;

@Service
public class AnalyticsService {

    private final AnalyticsRepository analyticsRepository;

    @Autowired
    public AnalyticsService(AnalyticsRepository analyticsRepository) {
        this.analyticsRepository = analyticsRepository;
    }

    public void recordClick(ShortURL shortURL, String clientIP,
                            Client clientUserAgent, String countryCode) {
        var click = AnalyticsRecord.builder()
                .shortURL(shortURL)
                .country(countryCode)
                .ip(clientIP)
                .browser(clientUserAgent.userAgent.family)
                .OS(clientUserAgent.os.family)
                .device(clientUserAgent.device.family)
                .build();
        analyticsRepository.save(click);
    }
}
