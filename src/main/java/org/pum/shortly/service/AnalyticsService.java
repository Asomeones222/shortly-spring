package org.pum.shortly.service;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import org.pum.shortly.model.AnalyticsRecord;
import org.pum.shortly.model.ShortURL;
import org.pum.shortly.repository.AnalyticsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua_parser.Client;

import java.io.IOException;
import java.net.InetAddress;

@Service
public class AnalyticsService {

    private final AnalyticsRepository analyticsRepository;
    private final DatabaseReader geoIP;

    @Autowired
    public AnalyticsService(AnalyticsRepository analyticsRepository,
                            DatabaseReader geoIP) {
        this.analyticsRepository = analyticsRepository;
        this.geoIP = geoIP;
    }

    public void recordClick(ShortURL shortURL, String clientIP,
                            Client clientUserAgent) {
        String countryCode = null;
        try {
            countryCode = geoIP.country(InetAddress.getByName(clientIP)).getCountry().getIsoCode();
        } catch (IOException | GeoIp2Exception e) {
        }

        var click = AnalyticsRecord.builder()
                .shortURL(shortURL)
                .country(countryCode)
                .ip(clientIP)
                .browser(clientUserAgent.userAgent != null ? clientUserAgent.userAgent.family : null)
                .OS(clientUserAgent.os != null ? clientUserAgent.os.family : null)
                .device(clientUserAgent.device != null ? clientUserAgent.device.family : null)
                .build();
        analyticsRepository.save(click);
    }
}
