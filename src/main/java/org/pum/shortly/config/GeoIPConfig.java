package org.pum.shortly.config;

import com.maxmind.geoip2.DatabaseReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;

@Configuration
public class GeoIPConfig {
    @Bean
    public DatabaseReader GeoIP() throws IOException {
        File database = new ClassPathResource("geoip/GeoLite2-Country.mmdb").getFile();
        return new DatabaseReader.Builder(database).build();
    }
}
