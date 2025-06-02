package org.pum.shortly.model;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
public class AnalyticsRecordDTO {
    private Long id;
    private ShortURL shortURL;
    private String country;
    private LocalDateTime localDateTime;
}
