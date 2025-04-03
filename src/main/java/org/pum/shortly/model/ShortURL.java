package org.pum.shortly.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShortURL {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @URL
    @NotNull
    @NotBlank
    private String mappedURL;
    @NotNull
    @NotBlank
    @Column(unique = true, nullable = false)
    private String code;
}
