package org.pum.shortly.dto;

import lombok.Builder;
import lombok.Data;
import org.pum.shortly.model.ShortURL;

import java.time.Instant;
import java.time.LocalDateTime;

@Data
@Builder
public class AnalyticsRecordDTO {
    private Long id;
    private ShortURL shortURL;
    private String country;
    private Instant accessTime;
    private String OS;
    private String device;
    private String browser;
}
