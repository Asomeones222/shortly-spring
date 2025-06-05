package org.pum.shortly.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

@Data
@Builder
public class ShortURLDTO {
    @NotBlank(message = "URL cannot be empty")
    @URL(message = "Invalid URL format")
    private String url;
    private String code;
}
