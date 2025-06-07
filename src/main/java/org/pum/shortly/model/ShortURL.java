package org.pum.shortly.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.pum.shortly.dto.ShortURLDTO;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(indexes = @Index(name = "idx_code", columnList = "code", unique = true))
public class ShortURL {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String url;
    @Column(nullable = false)
    private String code;

    public ShortURLDTO mapToDTO() {
        return ShortURLDTO.builder()
                .url(this.url)
                .code(this.code)
                .build();
    }
}
