package org.pum.shortly.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AnalyticsRecord {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "shorturl_id", nullable = false)
    private ShortURL shortURL;
    @NotNull
    private Instant accessTime;
    @NotNull
    private String ip;
    private String country;
    private String OS;
    private String device;
    private String browser;

    @PrePersist
    private void onCreate() {
        this.accessTime = Instant.now();
    }
}
