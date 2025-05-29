package org.pum.shortly.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class URLClick {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "shorturl_id", nullable = false)
    private ShortURL shortURL;
    private String country;
    @NotNull
    private LocalDateTime localDateTime;
}
